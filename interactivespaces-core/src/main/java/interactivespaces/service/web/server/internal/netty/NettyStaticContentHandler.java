/*
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
 */

package interactivespaces.service.web.server.internal.netty;

import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.setContentLength;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpCookie;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelFutureProgressListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.DefaultFileRegion;
import org.jboss.netty.channel.FileRegion;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.handler.stream.ChunkedFile;


import com.google.common.collect.Maps;

/**
 * Handle content for Netty.
 *
 * @author Keith M. Hughes
 */
public class NettyStaticContentHandler implements NettyHttpContentHandler {

	/**
	 * The parent content handler for this handler.
	 */
	private NettyWebServerHandler parentHandler;

	/**
	 * The URI prefix to be handled by this handler.
	 */
	private String uriPrefix;

	/**
	 * Base directory for content served by this handler.
	 */
	private File baseDir;


	/**
	 * Extra headers to add to the response.
	 */
	private Map<String, String> extraHttpContentHeaders = Maps.newHashMap();

	public NettyStaticContentHandler(NettyWebServerHandler parentHandler,
			String up, File baseDir, Map<String, String> extraHttpContentHeaders) {
		this.parentHandler = parentHandler;

		if (extraHttpContentHeaders != null) {
			this.extraHttpContentHeaders.putAll(extraHttpContentHeaders);
		}

		StringBuilder uriPrefix = new StringBuilder();
		if (!up.startsWith("/")) {
			uriPrefix.append('/');
		}
		uriPrefix.append(up);
		if (!up.endsWith("/")) {
			uriPrefix.append('/');
		}
		this.uriPrefix = uriPrefix.toString();

		this.baseDir = baseDir;
	}

	@Override
	public boolean isHandledBy(HttpRequest req) {
		return req.getUri().startsWith(uriPrefix);
	}

	@Override
	public void handleWebRequest(ChannelHandlerContext ctx, HttpRequest req, Set<HttpCookie> cookiesToAdd)
			throws IOException {
		String url = req.getUri();

		// Strip off query parameters, if any, as we don't care.
		int pos = url.indexOf('?');
		if (pos != -1)
			url = url.substring(0, pos);

		int luriPrefixLength = uriPrefix.length();
		String filepath = url.substring(url.indexOf(uriPrefix)
				+ luriPrefixLength);

		// TODO(keith): Make sure this doesn't allow wandering outside of the
		// file hierarchy rooted at baseDir (e.g. ../../.. type paths.
		File file = new File(baseDir, filepath);
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException fnfe) {
			parentHandler.sendError(ctx, NOT_FOUND);
			return;
		}
		long fileLength = raf.length();

		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
		parentHandler.addHttpResponseHeaders(response, extraHttpContentHeaders);
		if (cookiesToAdd != null) {
	      CookieEncoder encoder = new CookieEncoder(true);
	      for (HttpCookie value: cookiesToAdd) {
	        encoder.addCookie(NettyHttpResponse.createNettyCookie(value));
	        response.addHeader("Set-Cookie", encoder.encode());
	      }
		}

		setContentLength(response, fileLength);

		Channel ch = ctx.getChannel();

		// Write the initial line and the header.
		ch.write(response);

		// Write the content.
		ChannelFuture writeFuture;
		if (ch.getPipeline().get(SslHandler.class) != null) {
			// Cannot use zero-copy with HTTPS.
			writeFuture = ch.write(new ChunkedFile(raf, 0, fileLength, 8192));
		} else {
			// No encryption - use zero-copy.
			final FileRegion region = new DefaultFileRegion(raf.getChannel(),
					0, fileLength);
			writeFuture = ch.write(region);
			writeFuture.addListener(new ChannelFutureProgressListener() {
				@Override
				public void operationComplete(ChannelFuture future) {
					region.releaseExternalResources();
				}

				@Override
				public void operationProgressed(ChannelFuture arg0, long arg1,
						long arg2, long arg3) throws Exception {
					// Do nothing
				}

			});
		}

		// Decide whether to close the connection or not.
		if (!isKeepAlive(req)) {
			// Close the connection when the whole content is written out.
			writeFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}
}
