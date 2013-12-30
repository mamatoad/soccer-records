<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<table class="list">
    <tr>
        <th><s:label for="login" name="user.loginname"/></th>
        <td><s:text id="login" name="user.login"/></td>
    </tr>
    <tr>
        <th><s:label for="password" name="user.pass"/></th>
        <td><s:password id="password" name="user.password"/></td>
    </tr>
    <tr>
        <th><s:label for="passwordConfirmation" name="user.pass.confirm"/></th>
        <td><s:password id="passwordConfirmation" name="passwordConfirmation"/></td>
    </tr> 
    <tr>
        <th><s:label for="isAdmin" name="user.isAdmin"/></th>
        <td><s:checkbox id="isAdmin" name="isAdmin"/></td>
    </tr>
</table>