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
<title>Interactive Spaces Admin: Scripts</title>

<#include "/allpages_head.ftl">
</head>

<body>

<script type="text/javascript">
function doAjaxCommand(command) {
  $.ajax({
      url: '/interactivespaces/admin/namedscript/${script.id}/' + command + '.json',
      success: function(data) {
        $('#commandResult').html(data.result);
      }
  });
}

function deleteScript() {
    if (confirm("Are you sure you want to delete the script?")) {
        window.location='/interactivespaces/admin/namedscript/${script.id}/delete.html'
    }
}
</script>

<#include "/allpages_body_header.ftl">

<h1>Named Script: ${script.name}</h1>

<div class="commandBar"><ul>
<li><button type="button" onclick="doAjaxCommand('run')" title="Run the named script">Run</button></li>
<li><button type="button" id="editButton" onclick="window.location='/interactivespaces/admin/namedscript/${script.id}/edit.html'" title="Edit the script">Edit</button></li>
<li><button type="button" onclick="deleteScript()" title="Delete the script">Delete</button></li>
</ul></div>

<div id="commandResult">
</div>

<p>
${script.description}
</p>

<table>
<tr>
<th>ID</th>
<td>${script.id}</td>
</tr>
<tr>
<th>Language</th>
<td>${script.language}</td>
</tr>
<tr>
<th>Scheduled?</th>
<td><#if script.scheduled>Yes<#else>No</#if></td>
</tr>
<tr>
<th>Schedule</th>
<td>${script.schedule}</td>
</tr>
</table>

<h2>Content</h2>

<#if script.content?has_content>

<pre><code>
${script.content}
</code></pre>
</#if>

</body>
<html>
