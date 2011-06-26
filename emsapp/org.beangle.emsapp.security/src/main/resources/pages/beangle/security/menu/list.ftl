[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
[#include "../status.ftl"/]
[@b.grid  items=menus var="menu" sortable="false"]
[@b.gridbar title="菜单列表"]
	action.addParam('menu.profile.id',"${Parameters['menu.profile.id']!}");
	function activate(isActivate){
		return action.multi("activate","确定操作?","isActivate="+isActivate);
	}
	function preview(){
		window.open("${b.url('!preview')}?${b.paramstring}");
	}
	function redirectTo(url){window.open(url);}
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.edit")}",action.edit());
	bar.addItem("${b.text("action.freeze")}",activate(0),'${b.theme.iconurl('actions/freeze.png')}');
	bar.addItem("${b.text("action.activate")}",activate(1),'${b.theme.iconurl('actions/activate.png')}');
	bar.addItem("${b.text("action.export")}",action.exportData("code:代码,title:标题,entry:入口,remark:${b.text('common.remark')},enabled:是否可用"));
	bar.addItem("${b.text("action.delete")}",action.remove());
	bar.addItem("打印","preview()","print.png");
	bar.addItem("菜单配置","redirectTo('${b.url('menu-profile!search')}')");
[/@]
	[@b.row ]
		<tr [#if menu??] title="${(menu.remark?html)!}  ${menu.entry!}" id="${menu.code}"[/#if]>
		[@b.boxcol onclick="treeToggle(this,false)" /]
		[@b.col property="title" title="标题"]
		<div class="tier${menu.depth}" align="left">
		[#if (menu.children?size==0)]
			<a href="#" class="doc"/>
		[#else]
			<a href="#" class="folder_open" id="${menu.code}_folder" onclick="toggleRows(this)" >   </a>
		[/#if]
			[@b.a href="!info?menu.id=${menu.id}"]${menu.code} ${menu.title}[/@]
		</div>
		[/@]
		[@b.col property="engTitle" title="英文标题"/]
		[@b.col width="30%" title="使用资源"][#list menu.resources as re]${re.title?html}[#if re_has_next],[/#if][/#list][/@]
		[@b.col property="enabled" width="10%" title="common.status"][@enableInfo menu.enabled/][/@]
		</tr>
	[/@]
[/@]
<script type="text/javascript">
   //展开所有菜单
   displayAllRowsFor(1);
</script>
[@b.foot/]