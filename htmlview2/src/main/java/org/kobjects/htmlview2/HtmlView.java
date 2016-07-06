package org.kobjects.htmlview2;


import android.content.Context;
import android.graphics.Paint;

import elemental.html.Window;
import org.kobjects.css.CssProperty;
import org.kobjects.css.CssStyle;
import org.kobjects.css.CssStyleSheet;
import org.kobjects.css.CssUnit;

import java.net.URI;
import java.net.URISyntaxException;


/** 
 * Context information for a HTML document; holds references to the base URI and the style
 * sheet.
 */
public class HtmlView extends HtmlViewGroup implements Window {
  static final int PAINT_MASK = ~(Paint.STRIKE_THRU_TEXT_FLAG | Paint.UNDERLINE_TEXT_FLAG);

  final CssStyleSheet styleSheet = CssStyleSheet.createDefault();
  final RequestHandler requestHandler;
  float scale;
  public URI baseUri;
  HtmlDocument document;

  public HtmlView(Context androidContext, RequestHandler requestHandler, URI baseUri) {
    super(androidContext, null);
    this.requestHandler = requestHandler;
    this.baseUri = baseUri;
    scale = androidContext.getResources().getDisplayMetrics().density;
    htmlView = this;
  }

  int getTextSize(CssStyle style) {
    return Math.round(scale * style.get(CssProperty.FONT_SIZE, CssUnit.PX));
  }


  void setPaint(CssStyle style, Paint paint) {
    paint.setTextSize(getTextSize(style));
    paint.setTypeface(CssStyles.getTypeface(style));
    paint.setFlags((paint.getFlags() & PAINT_MASK) | CssStyles.getPaintFlags(style));
  }

  public URI createUri(String uri) throws URISyntaxException {
    return baseUri.resolve(uri);
  }

  public HtmlDocument getDocument() {
    if (document == null) {
      document = new HtmlDocument(this);
    }
    return document;
  }


  public RequestHandler getRequestHandler() { return requestHandler; }

  public CssStyleSheet getStyleSheet() {
    return styleSheet;
  }

  public URI getBaseUri() {
    return baseUri;
  }
}
