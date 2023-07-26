package com.example.MapIt.fileprocess;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.example.MapIt.repo.LoincRepo;
import com.example.MapIt.tests.LoincTest;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class LoincBatchprocess {
	 @Autowired
	 private JobBuilderFactory jobBuilderFactory;
	 @Autowired
	    private StepBuilderFactory stepBuilderFactory;
	 @Autowired
	    private LoincRepo loincRepository;


	    @Bean
	    public FlatFileItemReader<LoincTest> reader() {
	        FlatFileItemReader<LoincTest> itemReader = new FlatFileItemReader<>();
	        itemReader.setResource(new FileSystemResource("src/main/resources/LoincMasterDataSetCSV.csv"));
	        itemReader.setName("csvReader");
	        itemReader.setLinesToSkip(1);
	        itemReader.setLineMapper(lineMapper());
	        return itemReader;
	    }
     
	    private LineMapper<LoincTest> lineMapper() {
	        DefaultLineMapper<LoincTest> lineMapper = new DefaultLineMapper<>();

	        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	        lineTokenizer.setDelimiter(",");
	        lineTokenizer.setStrict(false);
	        lineTokenizer.setNames("LOINC_NUM", "COMPONENT_", "PROPERTY_", "TIME_ASPCT", "SYSTEM_", "SCALE_TYP", "METHOD_TYP", "CLASS_","CLASSTYPE_","LONG_COMMON_NAME","SHORTNAME_","STATUS_","VersionFirstReleased_","VersionLastChanged_");
	        
	        BeanWrapperFieldSetMapper<LoincTest> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	        fieldSetMapper.setTargetType(LoincTest.class);

	        lineMapper.setLineTokenizer(lineTokenizer);
	        lineMapper.setFieldSetMapper(fieldSetMapper);
	        return lineMapper;

	    }

	    @Bean
	    public Loincfileprocessor processor() {
	        return new Loincfileprocessor();
	    }

	    @Bean
	    public RepositoryItemWriter<LoincTest> writer() {
	        RepositoryItemWriter<LoincTest> writer = new RepositoryItemWriter<>();
	        writer.setRepository(loincRepository);
	        writer.setMethodName("save");
	        return writer;
	    }

	    @Bean
	    public Step step1() {
	        return stepBuilderFactory.get("csv-step").<LoincTest, LoincTest>chunk(10)
	                .reader(reader())
	                .processor(processor())
	                .writer(writer()) 
	                .build();
	    }

	    @Bean
	    public Job runJob() {
	        return jobBuilderFactory.get("importCustomers")
	                .flow(step1()).end().build();

	    }

	    @Bean
	    public TaskExecutor taskExecutor() {
	        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
	        asyncTaskExecutor.setConcurrencyLimit(10);
	        return asyncTaskExecutor;
	    }

}
