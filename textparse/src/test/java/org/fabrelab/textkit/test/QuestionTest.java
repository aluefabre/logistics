package org.fabrelab.textkit.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.fabrelab.textbreaker.BasicTextCutterTest;
import org.fabrelab.textbreaker.core.TextAnalyser;
import org.fabrelab.textbreaker.core.dic.Dictionary;
import org.fabrelab.textbreaker.core.grammer.Grammer;
import org.fabrelab.textkit.logistics.question.QuestionDictionaryLoader;
import org.fabrelab.textkit.logistics.question.QuestionGrammerLoader;

public class QuestionTest {
	
	public static void main(String[] args) {
		Dictionary dic = QuestionDictionaryLoader.loadDictionary();
		Grammer grammer = QuestionGrammerLoader.loadGrammer();
		TextAnalyser analyser = new TextAnalyser(dic, grammer);

//		analyser.analysis("请问一下广州物流发货到北京一般多少天？ ");
		
		analyser.analysis("送货送到至今是29号还送不到，，申通E物流公司是怎么搞的啊 ");
		analyser.analysis("浙江金华到甘肃甘谷货运得多少天 ");
		analyser.analysis("德邦和新邦的比较");
		analyser.analysis("德邦和新邦哪个比较好");
		try {
			InputStream is = BasicTextCutterTest.class.getResourceAsStream("/questions.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
			while(true){
				String line = reader.readLine();
				if(line==null){
					break;
				}
				else{
					analyser.analysis((line));
					
				}
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
