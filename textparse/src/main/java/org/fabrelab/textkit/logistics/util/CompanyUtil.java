package org.fabrelab.textkit.logistics.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanyUtil {

	public static class Company {

		public String name;
		public String code;

		public Company(String name, String code) {
			this.name = name;
			this.code = code;
		}

	}

	static public List<Company> parseCompanyCodes(String extractedText) {
		List<Company> result = new ArrayList<Company>();
		for (String name : companyCodes.keySet()) {
			if (extractedText.contains(name)) {
				String code = companyCodes.get(name);
				result.add(new Company(name, code));
			}
		}
		return result;
	}

	static HashMap<String, String> companyCodes = new HashMap<String, String>();
	static {
		companyCodes.put("邮政", "EMS");
		companyCodes.put("EMS", "EMS");
		companyCodes.put("德邦", "DEPPON");
		companyCodes.put("新邦", "XBWL");
		companyCodes.put("华宇", "HOAU");
		companyCodes.put("天地华宇", "HOAU");
		companyCodes.put("韵达", "YUNDA");
		companyCodes.put("申通", "STO");
		companyCodes.put("大田", "DTW");
		companyCodes.put("顺丰", "SFEXPRESS");
		companyCodes.put("圆通", "YTO");
		companyCodes.put("E物流", "STO");
		companyCodes.put("天天快递", "TTKDEX");
		companyCodes.put("安捷快递", "ANJELEX");
		companyCodes.put("佳吉", "JIAJI");
		companyCodes.put("佳怡", "JIAYI");
		companyCodes.put("苏粤", "SUYUE");
		companyCodes.put("盛辉", "SHENGHUI");
		companyCodes.put("远成", "EMS");
		companyCodes.put("中铁", "ZTWL");
		companyCodes.put("共速达", "EMS");
		companyCodes.put("港中能达", "ND56");
		companyCodes.put("邮政物流", "EMS");
		companyCodes.put("GLS", "GLS");
		companyCodes.put("京广速递", "KKE");
		companyCodes.put("龙邦", "LBEX");
		companyCodes.put("全日通", "ATEXPRESS");
		companyCodes.put("全际通 ", "QUANJT");
		companyCodes.put("速尔", "SURE");
		companyCodes.put("TNT", "TNT");
		companyCodes.put("UCS", "UCS");
		companyCodes.put("UPS", "UPS");
		companyCodes.put("鑫飞鸿", "XFHEX");
		companyCodes.put("运通", "YTEXPRESS");
		companyCodes.put("宅急送", "ZJS");
		companyCodes.put("中通", "ZTO");
		companyCodes.put("急先达", "JOUST");
		companyCodes.put("佳运美", "TMS");
		companyCodes.put("盛丰", "SFWL");
		companyCodes.put("DHL", "DHL");
		companyCodes.put("STO", "STO");
		companyCodes.put("ZTO", "ZTO");
		companyCodes.put("YTO", "YTO");
		companyCodes.put("邮政", "EMS");
		companyCodes.put("挂号信 ", "EMS");
		companyCodes.put("E邮宝", "EMS");
		companyCodes.put("汇通", "HTKY");
	}

}
