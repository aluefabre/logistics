package org.fabrelab.textkit.rules;

import org.fabrelab.textkit.model.ExtractResult;
import org.fabrelab.textkit.model.RuleExtractor;
import org.fabrelab.textkit.model.TraceText;
import org.fabrelab.textkit.tools.CommonEntityFilter;

public class DEPPON {

	public static final RuleExtractor rule0 = new RuleExtractor("(.*)ADDR(.*)发往(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule1 = new RuleExtractor("已到达(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule2 = new RuleExtractor("卫星定位(.*)车辆位置:(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule3 = new RuleExtractor("(.*)提货通知(.*)通知提货(.*)", "运输中", -1, -1);
	
	public static final RuleExtractor rule4 = new RuleExtractor("人工跟踪(.*)我司车辆(.*)ADDR(.*)预计到达时间为(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule5 = new RuleExtractor("人工跟踪(.*)车(.*)ADDR(.*)预计到达时间为(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule6 = new RuleExtractor("人工跟踪(.*)ADDR(.*)ADDR(.*)预计到达时间为(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule7 = new RuleExtractor("人工跟踪(.*)ADDR(.*)预计到达时间为(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule8 = new RuleExtractor("(.*)ADDR(.*)正常签收(.*)", "已签收", -1, -1);

	public static final RuleExtractor rule9 = new RuleExtractor("(.*)异常签收(.*)", "异常签收", -1, -1);
	
	public static final RuleExtractor rule10 = new RuleExtractor("签收人(.*)", "已签收", -1, -1);

	public static final RuleExtractor rule11 = new RuleExtractor("签\\s*收(.*)ADDR(.*)", "已签收", 0, -1);
	
	public static final RuleExtractor rule12 = new RuleExtractor("签\\s*收(.*)", "已签收", -1, -1);
	
	public static final RuleExtractor rule13 = new RuleExtractor("(.*)已收取货物(.*)ADDR(.*)库存中(.*)", "已揽件", 0, -1);
		
	public static final RuleExtractor rule14 = new RuleExtractor("(.*)预计到达时间(.*)", "运输中", -1, -1);
	
	public static final RuleExtractor rule15 = new RuleExtractor("(.*)ADDR(.*)收货开单(.*)", "已揽件", 0, -1);
		
	public static final RuleExtractor rule16 = new RuleExtractor("人工跟踪(.*)预计到达时间为(.*)", "运输中", -1, -1);
	
	public static final RuleExtractor rule17 = new RuleExtractor("(.*)CP(.*)发往(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule18 = new RuleExtractor("已到达(.*)CP(.*)", "运输中", -1, 1);
	
	public static final RuleExtractor rule19 = new RuleExtractor("(.*)ADDR(.*)到达(.*)ADDR(.*)来自(.*)ADDR(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule20 = new RuleExtractor("(.*)从(.*)ADDR(.*)发往(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule21 = new RuleExtractor("(.*)离开(.*)ADDR(.*)发往(.*)", "运输中", 0, -1);
	
	public static final RuleExtractor rule22 = new RuleExtractor("(.*)人工跟踪(.*)", "运输中", -1, -1);
	
	public static final RuleExtractor rule23 = new RuleExtractor("卫星定位(.*)车辆位置:(.*)", "运输中", -1, -1);
	
	public static final RuleExtractor rule24 = new RuleExtractor("卫星定位(.*)", "运输中", -1, -1);
	
	
	public static final RuleExtractor[] rules = new RuleExtractor[] {
		rule0, rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule11, rule12, rule13, rule14, rule15,
		rule16, rule17, rule18, rule19, rule20, rule21, rule22, rule23, rule24
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
