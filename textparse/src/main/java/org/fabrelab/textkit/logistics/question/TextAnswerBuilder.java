package org.fabrelab.textkit.logistics.question;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.pagekit.ali56.Ali56Client;
import org.fabrelab.textkit.logistics.util.AddressUtil;
import org.fabrelab.textkit.logistics.util.CompanyUtil;
import org.fabrelab.textkit.logistics.util.CompanyUtil.Company;
import org.fabrelab.textkit.logistics.util.MailNoUtil;

public class TextAnswerBuilder {

	public void process(LogisticsQuestion question) {
		if("TRACE".equals(question.getType())){
			processTraceQuestion(question);
		}
		if("CONTACE".equals(question.getType())){
			processNetworkQuestion(question);
		}
		if("ROUTE".equals(question.getType())){
			processRouteQuestion(question);
		}
		if("NETWORK".equals(question.getType())){
			processNetworkQuestion(question);
		}
	}

	private void processNetworkQuestion(LogisticsQuestion question) {
		List<Company> companies = CompanyUtil.parseCompanyCodes(question.getExtractedText());
		List<String> addresses = AddressUtil.parseAddresses(question.getExtractedText());
		
		String answer = "";
		
		if(companies==null || companies.size()==0){
			for (String addr : addresses) {
				String network = Ali56Client.getNetwork(addr);
				if(StringUtils.isNotBlank(network)){
					answer = addr + " 的货运网点信息\n " + answer + "\n" + network;;
				}
			}
		}else{
			for (Company company : companies) {
				for (String addr : addresses) {
					String network = Ali56Client.getNetwork(company.code, addr);
					if(StringUtils.isNotBlank(network)){
						answer = company.name + " " + addr + " 的货运网点信息\n\n " + network + "\n" + answer;
					}
				}
			}
		}
		if(StringUtils.isNotBlank(answer)){
			answer =  "以下是通过阿里物流网点查询得到的结果\n\n" 
					+ answer + "\n信息来自阿里巴巴物流平台，更多信息请百度“阿里物流网点”";
		}
		question.setAnswer(answer);
	}



	private void processRouteQuestion(LogisticsQuestion question) {
		List<Company> companies = CompanyUtil.parseCompanyCodes(question.getExtractedText());
		List<String> addresses = AddressUtil.parseAddresses(question.getExtractedText());
		
		String answer = "";
		
		if(companies==null || companies.size()==0){
			String route = Ali56Client.getRoute(addresses);
			if(StringUtils.isNotBlank(route)){
				answer = addresses + " 的货运线路信息\n " + answer + "\n" + route;;
			}
		}else{
			for (Company company : companies) {
				String route = Ali56Client.getRoute(company.code, addresses);
				if(StringUtils.isNotBlank(route)){
					answer =  company.name + " " + addresses + " 的货运线路信息\n\n " + route + "\n" + answer;
				}
			}
		}
		if(StringUtils.isNotBlank(answer)){
			answer = "以下是通过阿里物流运线查询得到的结果\n\n" 
					+ answer + "\n信息来自阿里巴巴物流平台，更多信息请百度“阿里物流运线”";
		}
		question.setAnswer(answer);
	}



	private void processTraceQuestion(LogisticsQuestion question) {
		List<Company> companies = CompanyUtil.parseCompanyCodes(question.getExtractedText());
		List<String> mailNos = MailNoUtil.parseMailNos(question.getExtractedText());
		
		String answer = "";
		
		for (Company company : companies) {
			for (String mailNo : mailNos) {
				String trace = Ali56Client.getTraceByCodeAndMailNo(company.code, mailNo);
				if(StringUtils.isNotBlank(trace)){
					answer = company.name + " [" + mailNo + "] 的跟踪信息\n\n " + trace + "\n" + answer;
				}
			}
		}
		if(StringUtils.isNotBlank(answer)){
			answer =  "以下是通过阿里物流跟踪查询得到的结果\n\n" 
					+ answer + "\n信息来自阿里巴巴物流平台，更多信息请百度“阿里物流跟踪”";
		}
		question.setAnswer(answer);
	}



	
}
