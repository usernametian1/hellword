package com.javen.controller;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.javen.model.DePartMentDto;
import com.javen.model.LowerDePartentDto;
import com.javen.model.OfficeBuildDto;
import com.javen.model.OfficeSpaceDto;
import com.javen.service.IDepartMentServcie;
import com.javen.service.ILowerParentService;
import com.javen.service.IOfficeBuildService;
import com.javen.service.IOfficeService;
import com.zhiyou.utils.PagingDto;
import com.zhiyou.utils.StringUtils;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/office")
public class OfficeSpaceController {
  
	@Autowired
	private IOfficeService officeService;
	
	@Autowired
	private IOfficeBuildService buildService;
	
	@Autowired
	private IDepartMentServcie dePartMServcie;
	@Autowired
	private ILowerParentService lowerParentService;
	
	
	@RequestMapping("/list")
	public String fidForPage(HttpServletRequest req,Model model,PagingDto page){
		Map<String ,Object> params = Utils.getParameters(req);
		String curPageNum = req.getParameter("curPageNum");
	    PagingDto paging = new PagingDto(req.getParameter("curPageNum"));
		    
		List<OfficeSpaceDto> officeList = new ArrayList<OfficeSpaceDto>();
		officeList = officeService.findForPage(params, paging);
		model.addAttribute("officeList", officeList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return "list";
	}
	
	
	@RequestMapping("/rInsert")
	public 	String doRInsert(HttpServletRequest req , Model model){
		List<OfficeBuildDto> buildList =	buildService.findAll();
		 List<DePartMentDto> parentList = dePartMServcie.findAllDepart();
		   model.addAttribute("parentList", parentList);  
		model.addAttribute("buildList", buildList);
		return "add";
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public JSONObject doInsert(HttpServletRequest req ,@RequestParam(value = "iconFile") MultipartFile iconFile){
        JSONObject json = new JSONObject();
		OfficeSpaceDto office = new OfficeSpaceDto();
        office.setUsername(req.getParameter("username"));
        office.setUserid(Integer.parseInt(req.getParameter("userid")));
        office.setFloor(Integer.parseInt(req.getParameter("floor")));
        office.setDivision(req.getParameter("division"));
        office.setRoom(Integer.parseInt(req.getParameter("room")));
        Date date = new Date();  
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
        String LgTime = sdformat.format(date);  
        office.setCreatTime(LgTime);
      
        String departmentid = req.getParameter("parentid");
        String sonpartmentid = req.getParameter("cup_channel_product_id");
        String seatdetail = req.getParameter("seatdetail");
        String bulidid = req.getParameter("buildid");
        if(!StringUtils.isEmpty(departmentid)){
        	office.setDepartmentid(Integer.parseInt(departmentid));
        }
        if(!StringUtils.isEmpty(sonpartmentid)){
        	office.setSonpartmentid(Integer.parseInt(sonpartmentid));
        }
         if(!StringUtils.isEmpty(seatdetail)){
        	 office.setSeatdetail(seatdetail);
         }
         if(!StringUtils.isEmpty(bulidid)){
        	 office.setBuildid(Integer.parseInt(bulidid));
         }
         OfficeSpaceDto officeDto = officeService.findOfficeByid(office.getUserid());
         if(officeDto != null){
        	 json.put("code", "203");
        	 return json;
         }
         try{
        	 officeService.insertSpace(office,iconFile);
        	 json.put("code", 200);
        	 return json;
         }catch(Exception e){
        	 e.printStackTrace();
        	 json.put("code", 201);
        	 return json;
         }
	}
	
	@RequestMapping("/redit/{userid}")
	public String doRupdate(@PathVariable("userid") int userid,Model model){
		OfficeSpaceDto office =   officeService.findOfficeByid(userid);
		 List<OfficeBuildDto> buildList =  buildService.findAll();
		 List<DePartMentDto> departMent = dePartMServcie.findAllDepart();
		model.addAttribute("office", office);
		model.addAttribute("buildList", buildList);
		model.addAttribute("departMent",departMent);
		return "edit";
	}

	@RequestMapping(value = "/doUpdate", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject doUpdate(OfficeSpaceDto office){
         JSONObject json = new JSONObject();
		 try{
			 officeService.doUpdate(office);
			 json.put("code", "200");
		 }catch(Exception e){
			 json.put("code", "201");
		 }
        
		return json;
	}
	
	@RequestMapping("/delete/{userid}/{curPageNum}")
	public  String doDelete(@PathVariable("userid") int userid,@PathVariable("curPageNum") String curPageNum){
		officeService.deleteOffice(userid);
		return "redirect:/office/list?curPageNum="+curPageNum;
	}
	
	@RequestMapping("/batchesDelete/{values}")
	public String doBatecheDelete(@PathVariable("values") String values){
	   String[] str = values.split(",");
	   for(String userid : str){
          officeService.deleteOffice(Integer.parseInt(userid));	       
	   }
		
		return "redirect:/office/list";
	}
	
	@RequestMapping("/getHead/{userid}")
	public String getHead(@PathVariable("userid") String userid,Model model){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		 OfficeSpaceDto office  = officeService.getOfficeSpace(params);
	    model.addAttribute("office", office);
	    return "icon";
	}
	
	
	@RequestMapping("/build")
	public String findBuild(HttpServletRequest req,Model model){
		Map<String ,Object> params = Utils.getParameters(req);
		 
		PagingDto paging = new PagingDto(req.getParameter("curPageNum"));
		List<OfficeBuildDto> buildList = new ArrayList<OfficeBuildDto>();
		buildList = buildService.findBuild(params, paging);
		model.addAttribute("buildList", buildList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return "building";
	}
	
	@RequestMapping("/rbuild")
	public String rBuild() {
		return "addbuild";
	}
	
	@RequestMapping("/buildInsert")
	@ResponseBody
	public JSONObject insertBuild(HttpServletRequest req,@RequestParam(value = "iconFile") MultipartFile iconFile) {
		JSONObject json = new JSONObject();
		OfficeBuildDto build = new OfficeBuildDto();
		build.setBuildname(req.getParameter("buildname"));
		try {
			buildService.insertBuild(build, iconFile);
			json.put("code", "200");
		}catch (Exception e) {
			e.printStackTrace();
			json.put("code", "201");
		}
		
		return json;
	}
	
	@RequestMapping("RUpdate/{buildid}")
	public String doREdit(@PathVariable("buildid") int buildid ,Model model) {
		OfficeBuildDto build = buildService.findBuildByid(buildid);
		model.addAttribute("build", build);
		return "editBuild";
	}
	
	@RequestMapping("updateBuild")
	@ResponseBody
	public JSONObject doUpdateBuild(HttpServletRequest req,@RequestParam("iconFile") MultipartFile iconFile) {
		JSONObject json = new JSONObject();
		int buildid = Integer.parseInt(req.getParameter("buildid"));
		try {
			buildService.doUpdateIcomFile(buildid,iconFile);
			json.put("code", "200");
		}catch(Exception e) {
			json.put("code", "201");
		}
		return json;
	}
	
	@RequestMapping("deleteBuild/{buildid}")
	public String doDeleteBuild(@PathVariable("buildid") int buildid){
		buildService.deleteBuild(buildid);
		return "redirect:/office/build";
	}
	
	
	@RequestMapping("/getBuildposion/{buildid}")
	public String doBuildPosion(@PathVariable("buildid") int buildid , Model model){
	  OfficeBuildDto build = buildService.findBuildByid(buildid);
	  model.addAttribute("build", build);
	  return "buildIcon";
	}
	
	
	@RequestMapping("/plane/{room}")
	public String doPlane(@PathVariable("room") String room,Model model){
		String roomid =room.substring(room.length()-2);
		model.addAttribute("roomid", roomid);
		return "planeroom";
	}
	
	@RequestMapping("/show")
	public String doShowDepart(HttpServletRequest req,Model model){
	  List<DePartMentDto> parentList = dePartMServcie.findAllDepart();
	   model.addAttribute("parentList", parentList);  
		return "showdepartment";
	}
	
	
	@RequestMapping("/showLower")
    @ResponseBody
	public  JSONObject doFindLower(HttpServletRequest req ){
		JSONObject json = new JSONObject();
		String parentid = req.getParameter("parentid");
		if(StringUtils.isEmpty(parentid)){
			  json.put("201", "部门为空");
			  return json;
		}
		try{
		   List<LowerDePartentDto> lowerList = lowerParentService.findAllLower(Integer.parseInt(parentid));
			if(lowerList != null && lowerList.size() >0){
				json.put("code", 200);
				json.put("msg", lowerList);
				return json;
			}else{
				json.put("code", 202);
				json.put("msg", "下面没部门");
				return json;	
			}
		}catch(Exception e){
			e.printStackTrace();
			json.put("203", "出异常");
			return json;
		}

	}
	
	
	@RequestMapping("/editPortrait/{userid}")
	public String doEditPortrait(@PathVariable("userid") int userid,Model model){
         model.addAttribute("userid", userid);
		
		return "/editPortrait";
	}
	
	
	@RequestMapping("/doPortrait")
	@ResponseBody
	public JSONObject doEditPor(@RequestParam(value = "iconFile") MultipartFile iconFile,@RequestParam(value="userid") int userid){
		JSONObject json = new JSONObject();
		try{
			officeService.doEditPortrait(iconFile,userid);
			json.put("code", 200);
			return json;
		}catch(Exception e){
			e.printStackTrace();
			json.put("code",201);
			return json;
		}
	}
	
	
	@RequestMapping("/json")
	public String doModle(String pads, Model model,HttpServletRequest req,HttpServletResponse res){
		return "/testaxja";
	}
	
}
