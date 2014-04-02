package ykw.jenkins02.selenium_server.script;

public abstract class ScriptSelenium {
	
	/**返回job工程名集合*/
	public final static String RETURN_JOBNAMELIST_SCRIPT=  "var tbody = document.getElementById('projectstatus').firstElementChild;"
			+ "var trs = tbody.children;"
			+ "var list = [];"
			+ "for(var i=0;i<trs.length-1;i++){"
			+ "	var each = trs[i+1].id;"
			+ "   list[i]=each.substring(4);"
			+ "}"
			+ "return list;";
	
	/**返回subVersion单选按钮*/
	public final static String RETURN_SUBVERSION_RADIO_SCRIPT="var classElement = document.getElementsByName('scm');"
			+ "var subVersionRadio;"
			+ "if(classElement){"
			+ "	for(var i=0;i<classElement.length;i++){"
			+ "		var eachElement = classElement.item(i);"
			+ "		var textElement = eachElement.nextSibling;"
			+ "		if(textElement && textElement.nodeType == 3 && textElement.textContent.trim() == 'Subversion'){"
			+ "			subVersionRadio = eachElement;"
			+ "		}"
			+ "	}"
			+ "}"
			+ "return subVersionRadio;";
	
	/**返回ssh site选择框*/
	public final static String RETURN_SSHSITE_SELECT_SCRIPT="var setting_name = document.getElementsByClassName('setting-name');"
			+ "var selectEle;"
			+ "for(var i=0;i<setting_name.length;i++){"
			+ "	var each = setting_name.item(i);"
			+ "	if(each && each.textContent && each.textContent.trim() == 'SSH site'){"
			+ "		selectEle = each.nextSibling.firstChild;"  // TODO webdriver取不到
			+ "	}"
			+ "}"
			+ "return selectEle;";
	
	/**返回ssh命令*/
	public final static String RETURN_SSHSITE_COMMAND_SCRIPT = "var texts = document.getElementsByTagName('textarea');"
			+ "var textEle;"
			+ "for(var i=0;i<texts.length;i++){"
			+ "	var each = texts.item(i);"
			+ "	if(each.className.trim() == 'setting-input' && each.name.trim() == '_.command'){"  // 根据class和name属性定位
			+ "		textEle = each;"
			+ "	}"
			+ "}"
			+ "return textEle;";

	/**返回maven命令*/
	public final static String RETURN_MAVNE_COMMAND = "var goal = document.getElementsByName('goals')[0];"
		+ "if(goal){"
		+ "  return goal.getAttribute('value');"
		+ "}else{"
		+ "  return '';"
		+ "}";
	
	/**返回ssh server name*/
	public final static String RETURN_SSH_SERVER_NAME=""
			+ "var sshNameSelect = document.querySelector('select.setting-input.ssh-config-name');"
			+ "return sshNameSelect.selectedIndex;"; //TODO
}