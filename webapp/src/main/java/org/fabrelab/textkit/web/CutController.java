package org.fabrelab.textkit.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fabrelab.textbreaker.basic.BasicDictionaryLoader;
import org.fabrelab.textbreaker.basic.BasicTextCutter;
import org.fabrelab.textbreaker.core.model.Word;
import org.fabrelab.textkit.service.CutService;
import org.fabrelab.pagekit.MappingUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class CutController extends MultiActionController{
	
	CutService cutService;
	
	private static final Logger log = Logger.getLogger(CutController.class.getName());
		
	public ModelAndView cut(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("cut", "model", model);	
	}
	
	public ModelAndView cutSubmit(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		String text = MappingUtil.parseString(request, "text");
		Boolean greedy = MappingUtil.parseBoolean(request, "greedy");
		
		long begin = System.currentTimeMillis();
		
		String[] items = text.split("\n");
		
		List<List<Word>> allResults = new ArrayList<List<Word>>();
		for(String item : items){
			List<Word> results = cutService.cut2ListWithAnnotation(item, greedy);
			allResults.add(results);
		}
		
		
		long end = System.currentTimeMillis();
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("allResults", allResults);
		model.put("text", text);
		model.put("greedy", greedy);
		model.put("time", end - begin);
		
		return new ModelAndView("cut", "model", model);	
	}

	public void setCutService(CutService cutService) {
		this.cutService = cutService;
	}
	
}