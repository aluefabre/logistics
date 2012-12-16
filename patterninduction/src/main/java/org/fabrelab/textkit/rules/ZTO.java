package org.fabrelab.textkit.rules;

import org.fabrelab.textkit.model.ExtractResult;
import org.fabrelab.textkit.model.RuleExtractor;
import org.fabrelab.textkit.model.TraceText;
import org.fabrelab.textkit.tools.CommonEntityFilter;

public class ZTO {

	public static final RuleExtractor rule0 = new RuleExtractor("ADDR上一站是ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule1 = new RuleExtractor("(.*)上一站是(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule2 = new RuleExtractor("已签收 签收人是(.*)签收(.*)是:(.*)ADDR(.*)", "签收", 0, -1);
	
	public static final RuleExtractor rule3 = new RuleExtractor("(.*)CP正在派件", "运输中", -1, 1);
	
	public static final RuleExtractor rule4 = new RuleExtractor("(.*)正在派件", "运输中", -1, 1);
	
	public static final RuleExtractor rule5 = new RuleExtractor("已签收 签收人是(.*)签收(.*)是:(.*)", "签收", -1, 1);
	
	public static final RuleExtractor[] rules = new RuleExtractor[] {
		rule0, rule1, rule2, rule3, rule4, rule5
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
