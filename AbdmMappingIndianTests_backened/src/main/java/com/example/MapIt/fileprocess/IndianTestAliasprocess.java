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

import com.example.MapIt.repo.IndianTestRepo;
import com.example.MapIt.repo.LoincRepo;
import com.example.MapIt.tests.IndianTest;
import com.example.MapIt.tests.LoincTest;

import lombok.AllArgsConstructor;
@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class IndianTestAliasprocess {
  
	@Autowired
	 private JobBuilderFactory jobBuilderFactory;
	 @Autowired
	    private StepBuilderFactory stepBuilderFactory;
	 @Autowired
	    private IndianTestRepo loincRepository;


	    @Bean
	    public FlatFileItemReader<IndianTest> readerIn() {
	        FlatFileItemReader<IndianTest> itemReader = new FlatFileItemReader<>();
	        itemReader.setResource(new FileSystemResource("src/main/resources/LOINCandAlias_india.csv"));
	        itemReader.setName("csvReader");
	        itemReader.setLinesToSkip(1);
	        itemReader.setLineMapper(lineMapper());
	        return itemReader;
	    }
    
	    private LineMapper<IndianTest> lineMapper() {
	        DefaultLineMapper<IndianTest> lineMapper = new DefaultLineMapper<>();

	        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	        lineTokenizer.setDelimiter(",");
	        lineTokenizer.setStrict(false);
	        lineTokenizer.setNames("LOINC_Code","Aliases_");
	        lineTokenizer.setIncludedFields(new int[] {1,3});
	        BeanWrapperFieldSetMapper<IndianTest> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	        fieldSetMapper.setTargetType(IndianTest.class);
	        lineMapper.setLineTokenizer(lineTokenizer);
	        lineMapper.setFieldSetMapper(fieldSetMapper);
	        return lineMapper;
	    }
	    @Bean
	    public IndianTestfileprocessor processorIn() {
	        return new IndianTestfileprocessor();
	    }

	    @Bean
	    public RepositoryItemWriter<IndianTest> writerIn() {
	        RepositoryItemWriter<IndianTest> writer = new RepositoryItemWriter<>();
	        writer.setRepository(loincRepository);
	        writer.setMethodName("save");
	        return writer;
	    }

	    @Bean
	    public Step step1In() {
	        return stepBuilderFactory.get("csv-step").<IndianTest, IndianTest>chunk(10)
	                .reader(readerIn())
	                .processor(processorIn())
	                .writer(writerIn()) 
	                .build();
	    }



	    @Bean
	    public TaskExecutor taskExecutorIn() {
	        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
	        asyncTaskExecutor.setConcurrencyLimit(10);
	        return asyncTaskExecutor;
	    }
}
