package org.fabrelab.textkit.rules;

import org.fabrelab.textkit.model.ExtractResult;
import org.fabrelab.textkit.model.RuleExtractor;
import org.fabrelab.textkit.model.TraceText;
import org.fabrelab.textkit.tools.CommonEntityFilter;

public class STO {
	public static final RuleExtractor rule0 = new RuleExtractor("由(.*)ADDR(.*)发往(.*)ADDR(.*)", "运输中", 0, -1);
	public static final RuleExtractor rule1 = new RuleExtractor("由(.*)ADDR(.*)正在进行(.*)扫描(.*)", "运输中", 0, -1);
	public static final RuleExtractor rule2 = new RuleExtractor("快件已到达(.*)ADDR(.*)扫描员是(.*)上一站是(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule3 = new RuleExtractor("(.*)ADDR(.*)ADDR(.*)的收件员(.*)已收件(.*)", "已揽件", -2, -1);
	public static final RuleExtractor rule4 = new RuleExtractor("(.*)ADDR(.*)的收件员(.*)已收件(.*)", "已揽件", 0, -1);
	
	public static final RuleExtractor rule5 = new RuleExtractor("(.*)签收(.*)签收人是(.*)", "已签收", -1, -1);
	
	public static final RuleExtractor rule6 = new RuleExtractor("(.*)ADDR(.*)的派件员(.*)正在派件(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule7 = new RuleExtractor("(.*)由(.*)ADDR(.*)ADDR(.*)发往(.*)", "运输中", -2, -1);
	
	public static final RuleExtractor rule8 = new RuleExtractor("(.*)由(.*)ADDR(.*)发往(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule9 = new RuleExtractor("(.*)快件异常签收(.*)", "异常签收", 0, -1);
	
	public static final RuleExtractor rule10 = new RuleExtractor("(.*)CP(.*)的收件员(.*)已收件(.*)", "已揽件", -1, 1);
	
	public static final RuleExtractor rule11 = new RuleExtractor("(.*)CP(.*)的派件员(.*)正在派件(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule12 = new RuleExtractor("快件已到达(.*)CP(.*)扫描员是(.*)上一站是(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule13 = new RuleExtractor("由(.*)CP(.*)发往(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule14 = new RuleExtractor("由(.*)CP(.*)正在进行(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule15 = new RuleExtractor("(.*)的收件员(.*)已收件(.*)", "运输中", -1, -1);
	
	public static final RuleExtractor[] rules = new RuleExtractor[] {
		rule0, rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule11, rule12, rule13, rule14, rule15
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
