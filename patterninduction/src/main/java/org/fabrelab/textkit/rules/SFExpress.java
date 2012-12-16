package org.fabrelab.textkit.rules;

import org.fabrelab.textkit.model.ExtractResult;
import org.fabrelab.textkit.model.RuleExtractor;
import org.fabrelab.textkit.model.TraceText;
import org.fabrelab.textkit.tools.CommonEntityFilter;

public class SFExpress {
	
	public static final RuleExtractor rule0 = new RuleExtractor("快件由于(.*)派送不成功(.*)", "运输中", -1, -1);
	
	public static final RuleExtractor rule1 = new RuleExtractor("快件离开(.*)ADDR(.*)快件离开(.*)ADDR", "运输中", 1, -1);
	public static final RuleExtractor rule2 = new RuleExtractor("快件离开(.*)ADDR(.*)正在分拣", "运输中", 0, -1);
	
	public static final RuleExtractor rule3 = new RuleExtractor("快件离开(.*)ADDR(.*)ADDR", "运输中", -2, -1);
	public static final RuleExtractor rule4 = new RuleExtractor("快件到达(.*)ADDR(.*)ADDR", "运输中", -2, -1);
	
	public static final RuleExtractor rule5 = new RuleExtractor("快件离开(.*)ADDR(.*)", "运输中", 0, -1);
	public static final RuleExtractor rule6 = new RuleExtractor("快件到达(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule7 = new RuleExtractor("快件到达", "运输中", -1, -1);
	public static final RuleExtractor rule8 = new RuleExtractor("快件离开", "运输中", -1, -1);
	
	public static final RuleExtractor rule9 = new RuleExtractor("快件离开(.*)", "运输中", -1, 1);
	public static final RuleExtractor rule10 = new RuleExtractor("快件到达(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule12 = new RuleExtractor("已取件", "已揽件",  -1, -1);
	public static final RuleExtractor rule13 = new RuleExtractor("正在派件", "运输中",  -1, -1);
	public static final RuleExtractor rule14 = new RuleExtractor("派件已签收", "已签收", -1, -1);
	
	public static final RuleExtractor[] rules = new RuleExtractor[] {
		rule0, rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule12, rule13, rule14
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
