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
<title>Interactive Spaces Admin: Live Activity Groups</title>

<#include "/allpages_head.ftl">
</head>

<body>

<#include "/allpages_body_header.ftl">

<h1>Live Activity Groups</h1>

<div class="commandBar"><ul>
<li><button type="button" id="newButton" onclick="window.location='/interactivespaces/liveactivitygroup/new.html?mode=embedded'" title="Create a new live activity group">New</button></li>
</ul></div>

<div id="commandResult">
</div>

<ul>
<#list liveactivitygroups as liveactivitygroup>
    <li><a href="${liveactivitygroup.id}/view.html">${liveactivitygroup.name}</a></li>
</#list>
</ul>

</body>
<html>