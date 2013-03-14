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
<title>Interactive Spaces Admin: Troubleshoot Topics</title>

<#include "/allpages_head.ftl">
</head>

<body>

<#include "/allpages_body_header.ftl">

<h1>Troubleshoot Topics</h1>

<ul>
<#list topics as topic>
    <li>${topic.topicName}</li>
    <ul>
        <li>Publishers</li>
        <ul>
<#list topic.publishers as publisher>
            <li>${publisher}</li>
</#list>
        </ul>
        <li>Subscribers</li>
        <ul>
<#list topic.subscribers as subscriber>
            <li>${subscriber}</li>
</#list>
        </ul>
    </ul>
</#list>
</ul>

</body>
<html>