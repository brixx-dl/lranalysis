package de.tqs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.tqs.excelread.ExcelReading;





@Controller
@ComponentScan("xlsread")
public class XlsReadController {

	@GetMapping(value="/xlsRead")
	@ResponseBody
	public void xlsRead(@RequestParam("file")String filename){
	
		ExcelReading.ExcelStream(filename);
			}	
	
}
