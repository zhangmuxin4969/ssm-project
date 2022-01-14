<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面</span>
    </div>
    <div class="search">
        <form method="post" action="${pageContext.request.contextPath }/getUserLisByPage?inputPage=1">
            <span>用户编码：</span>
            <input name="userCode" type="text" value="${user.userCode }">

            <span>用户名称：</span>
            <input name="userName" type="text" value="${user.userName }">

            <span>性别：</span>
            <select name="gender">
                <option value="0">--请选择--</option>
                <option value="1" ${user.gender == 1 ? "selected=\"selected\"":"" }>男</option>
                <option value="2" ${user.gender == 2 ? "selected=\"selected\"":"" }>女</option>
            </select>
            <span>角色：</span>
            <select name="userRole">
                <c:if test="${user != null }">
                    <option value="0">--请选择--</option>
                    <c:forEach var="role" items="${roleList}">
                        <option <c:if test="${user.userRole == role.id }">selected="selected"</c:if>
                                value="${role.id}">${role.roleName}</option>
                    </c:forEach>
                </c:if>
            </select>
            <input value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath }/jsp/useradd.jsp">添加用户</a>
        </form>
    </div>
    <!--用户操作表格-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">用户编码</th>
            <th width="10%">用户名称</th>
            <th width="10%">性别</th>
            <th width="10%">电话</th>
            <th width="10%">地址</th>
            <th width="10%">创建时间</th>
            <th width="10%">用户角色</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="user" items="${userList }" varStatus="status">
            <tr>
                <td>
                    <span>${user.userCode }</span>
                </td>
                <td>
                    <span>${user.userName }</span>
                </td>
                <td>
						<span>
            		<c:if test="${user.gender == 1 }">男</c:if>
					<c:if test="${user.gender == 2 }">女</c:if>
						</span>
                </td>
                <td>
                    <span>${user.phone}</span>
                </td>
                <td>
                    <span>${user.address}</span>
                </td>
                <td>
					<span>
					<fmt:formatDate value="${user.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
                </td>
                <td>
                    <span>
                        <c:forEach var="role" items="${roleList}">
                            <c:if test="${role.id == user.userRole}">${role.roleName}</c:if>
                        </c:forEach>
                    </span>
                </td>
                <td>
                    <span><a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
                            src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
                            src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
                            src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <%@include file="/jsp/rollpage.jsp" %>
</div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain" >
            <p>你确定要删除用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/userlist.js"></script>

