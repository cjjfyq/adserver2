
<%@ page contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ page import="java.awt.*, java.awt.image.*, java.util.*, java.io.*" %>
<%
try {
	response.setContentType("image/jpeg");
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);

	int height=22, width=60;
	BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	Graphics gs = bi.getGraphics();
	gs.setColor(getRandColor(180, 250));
	gs.fillRect(0,0,width,height);
	String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	Random rand = new Random();
    int size = base.length();
    StringBuffer str = new StringBuffer();    
    gs.setFont(new Font("ºÚÌå", Font.BOLD, 19));
    for (int i = 0; i < 4; i++) {
    	int start = rand.nextInt(size);
    	String tmpStr = base.substring(start, start + 1);
    	str.append(tmpStr);    
    	gs.setColor(getRandColor(10, 150));
    	gs.drawString(tmpStr, 13 * i + 6 + rand.nextInt(5), 14 + rand.nextInt(6));
    }
    
  String image = new String(str);
	session.setAttribute("validate", image);
  OutputStream os = response.getOutputStream();    
	com.sun.image.codec.jpeg.JPEGImageEncoder en = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(os);
	en.encode(bi);
	
	os.flush();
	os.close();
	out.clear();
	out = pageContext.pushBody();
} catch (Exception e) {}
%>
<%!
Color getRandColor(int fc, int bc) {
	Random random = new Random();
	if (fc > 255)
		fc = 255;
	if (bc > 255)
		bc = 255;
	int r = fc + random.nextInt(bc - fc);
	int g = fc + random.nextInt(bc - fc);
	int b = fc + random.nextInt(bc - fc);
	return new Color(r, g, b);
}
%>











