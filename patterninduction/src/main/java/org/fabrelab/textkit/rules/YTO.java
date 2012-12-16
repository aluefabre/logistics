package org.fabrelab.textkit.rules;

import org.fabrelab.textkit.model.ExtractResult;
import org.fabrelab.textkit.model.RuleExtractor;
import org.fabrelab.textkit.model.TraceText;
import org.fabrelab.textkit.tools.CommonEntityFilter;

public class YTO {

	public static final RuleExtractor rule0 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)揽收扫描(.*)", "已揽件", -2, -1);
	public static final RuleExtractor rule1 = new RuleExtractor("(.*)ADDR(.*)揽收扫描(.*)", "已揽件", 0, -1);
	
	public static final RuleExtractor rule2 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)正常签收(.*)扫描(.*)", "已签收", -2, -1);
	
	public static final RuleExtractor rule3 = new RuleExtractor("(.*)ADDR(.*)正常签收(.*)扫描(.*)", "已签收", 0, -1);
	
	public static final RuleExtractor rule4 = new RuleExtractor("(.*)ADDR(.*)失败签收扫描(.*)", "异常签收", 0, -1);
	public static final RuleExtractor rule5 = new RuleExtractor("(.*)ADDR(.*)异常签收扫描(.*)", "异常签收", 0, -1);
	
	public static final RuleExtractor rule6 = new RuleExtractor("(.*)ADDR(.*)签收录入扫描(.*)", "已签收", 0, -1);
	
	public static final RuleExtractor rule7 = new RuleExtractor("(.*)ADDR(.*)派件扫描(.*)派件人(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule8 = new RuleExtractor("(.*)ADDR(.*)准备派送(.*)派件人(.*)", "运输中", 0, -1);


	public static final RuleExtractor rule9 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)业务员收件(.*)", "已揽件", -2, -1);
	
	public static final RuleExtractor rule10 = new RuleExtractor("(.*)ADDR(.*)业务员收件(.*)", "已揽件", 0, -1);
	

	public static final RuleExtractor rule11 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)扫描(.*)", "运输中", -2, -1);
	
	public static final RuleExtractor rule12 = new RuleExtractor("(.*)ADDR(.*)扫描(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule13 = new RuleExtractor("(.*)CP(.*)扫描(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule14 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)", "运输中", -2, -1);
	
	public static final RuleExtractor rule15 = new RuleExtractor("(.*)ADDR(.*)", "运输中", 0, -1);
	
	
	public static final RuleExtractor[] rules = new RuleExtractor[] {
		rule0, rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule11, rule12, rule13, rule14, rule15
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
