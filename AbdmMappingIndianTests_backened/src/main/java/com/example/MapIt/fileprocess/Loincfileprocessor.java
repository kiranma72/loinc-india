package com.example.MapIt.fileprocess;

import org.springframework.batch.item.ItemProcessor;

import com.example.MapIt.tests.LoincTest;


public class Loincfileprocessor implements ItemProcessor<LoincTest,LoincTest> {

    @Override
    public LoincTest process(LoincTest test) throws Exception {

    	return test ;
}
}
