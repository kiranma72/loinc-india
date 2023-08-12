package com.example.MapIt.fileprocess;

import org.springframework.batch.item.ItemProcessor;

import com.example.MapIt.tests.IndianTest;

public class IndianTestfileprocessor implements ItemProcessor<IndianTest,IndianTest> {

    @Override
    public IndianTest process(IndianTest test) throws Exception {

    	return test ;
}

}
