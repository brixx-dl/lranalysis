package de.tqs;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.tqs.excelread.ExcelReading;
import springfox.documentation.spring.web.json.Json;

@Controller
@ComponentScan("xlsread")

public class XlsReadController {

	// Auslesen der VUser
	//@GetMapping(value = "/VUserRead")

	@RequestMapping(value = "/getAnalyse", method = RequestMethod.GET, produces = "text/html")
	
	@ResponseBody
	public StringBuilder getAnalyse(
			@RequestParam("Dateiname-VUser") String filenvuser,
			@RequestParam("Dateiname-Results") String fileresults) {
	//ExcelReading.ExcelStream(filenvuser, fileresults);

	 return ExcelReading.ExcelStream(filenvuser, fileresults);
	
	}

	
	
}
