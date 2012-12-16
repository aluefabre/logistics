package org.fabrelab.textkit.rules;

import org.fabrelab.textkit.model.ExtractResult;
import org.fabrelab.textkit.model.RuleExtractor;
import org.fabrelab.textkit.model.TraceText;
import org.fabrelab.textkit.tools.CommonEntityFilter;

public class YUNDA {

	public static final RuleExtractor rule0 = new RuleExtractor("(.*)ADDR(.*)进行(.*)并发往(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule1 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)揽收", "已揽件", -2, -1);
	
	public static final RuleExtractor rule2 = new RuleExtractor("(.*)ADDR(.*)揽收", "已揽件", 0, -1);
	

	public static final RuleExtractor rule3 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)开始派送(.*)", "运输中", -2, -1);
	
	public static final RuleExtractor rule4 = new RuleExtractor("(.*)ADDR(.*)开始派送(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule5 = new RuleExtractor("(.*)ADDR(.*)到达(.*)上级地点(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule6 = new RuleExtractor("(.*)ADDR(.*)扫描(.*)并发往(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule7 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)派送扫描(.*)上级地点(.*)", "运输中", -2, -1);
	
	public static final RuleExtractor rule8 = new RuleExtractor("(.*)ADDR(.*)派送扫描(.*)上级地点(.*)", "运输中", 0, -1);
	

	public static final RuleExtractor rule9 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)由(.*)签收", "已签收", -2, -1);
	
	public static final RuleExtractor rule10 = new RuleExtractor("(.*)ADDR(.*)由(.*)签收", "已签收", 0, -1);
	
	public static final RuleExtractor rule11 = new RuleExtractor("(.*)ADDR(.*)称重扫描", "运输中", 0, -1);
	
	public static final RuleExtractor rule12 = new RuleExtractor("(.*)ADDR(.*)发往(.*)ADDR(.*)", "运输中", 0, -1);

	public static final RuleExtractor rule13 = new RuleExtractor("(.*)ADDR(.*)到达(.*)上级地点(.*)", "运输中", 0, -1);
	

	public static final RuleExtractor rule14 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)签收异常(.*)", "异常签收", -2, -1);
	
	public static final RuleExtractor rule15 = new RuleExtractor("(.*)ADDR(.*)签收异常(.*)", "异常签收", 0, -1);
	
	public static final RuleExtractor rule16 = new RuleExtractor("(.*)ADDR(.*)扫描(.*)并发往(.*)", "运输中", 0, -1);
	
	
	public static final RuleExtractor rule17 = new RuleExtractor("(.*)ADDR(.*)进行(.*)并发往(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule18 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)发往ADDR(.*)", "运输中", -2, -1);
	
	public static final RuleExtractor rule19 = new RuleExtractor("(.*)ADDR(.*)发往ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule20 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)", "运输中", -2, -1);
	public static final RuleExtractor rule21 = new RuleExtractor("(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule22 = new RuleExtractor("(.*)揽收(.*)", "已揽件", -1, -1);
	public static final RuleExtractor rule23 = new RuleExtractor("(.*)称重扫描(.*)", "运输中", -1, -1);
	
	public static final RuleExtractor[] rules = new RuleExtractor[] {
		rule0, rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule11, rule12, rule13, rule14, rule15,
		rule16, rule17, rule18, rule19, rule20,rule21, rule22, rule23
	};
	public static ExtractResult extract(String text){
		TraceText traceText = CommonEntityFilter.filterCommonEntities(text);
		for(RuleExtractor re : rules){
			ExtractResult result = re.extract(traceText);
			if(result.isSuccess()){
				return result;
			}
		}
		return new ExtractResult("","",false);
	}

}
