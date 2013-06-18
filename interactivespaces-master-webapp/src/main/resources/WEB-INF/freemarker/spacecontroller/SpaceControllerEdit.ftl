<#--
 * Copyright (C) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 -->
<!DOCTYPE html>
<html>
<head>
<title>Interactive Spaces Admin: Space Controllers</title>

<#include "/allpages_head.ftl" >
</head>

<body>

<#include "/allpages_body_header.ftl">

<h1>Edit Space Controller: ${spacecontroller.name}</h1>

<form  method="post">

<table>
<tr>
<th>UUID</th>
<td>${spacecontroller.uuid}</td>
</tr>
<tr>
<td>Name</td>
<td>
<@spring.formInput path="spacecontroller.name" />
<@spring.showErrors '<br>', 'fieldError' />
 </td>

</tr>
<tr>
<td valign="top">Description</td><td><@spring.formTextarea path="spacecontroller.description" attributes='rows="5" cols="40"' /></td>
</tr>
<tr>
<td>Host ID</td>
<td>
<@spring.formInput path="spacecontroller.hostId" />
<@spring.showErrors '<br>', 'fieldError' />
 </td>

<tr>
<th>&nbsp;</th>
<td>
<input type="submit" value="Save" />
<button type="button" id="cancelButton" onclick="window.location='/interactivespaces/spacecontroller/${id}/view.html'" title="Cancel the edit">Cancel</button>
</td>
</table>

</form>

</body>
<html>