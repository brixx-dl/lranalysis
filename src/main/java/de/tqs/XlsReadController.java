package de.tqs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import de.tqs.excelread.ExcelReading;


@Controller
@ComponentScan("xlsread")

public class XlsReadController {

	// Auslesen der VUser
	// @GetMapping(value = "/VUserRead")

	@RequestMapping(value = "/getAnalyse", method = RequestMethod.GET, produces = "text/html")

	@ResponseBody
	public StringBuilder getAnalyse(@RequestParam("Dateiname-VUser") String filenvuser,
			@RequestParam("Dateiname-Results") String fileresults) {

		return ExcelReading.ExcelStream(filenvuser, fileresults);

	}

}
