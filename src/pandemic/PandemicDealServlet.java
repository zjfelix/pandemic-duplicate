package pandemic;

import java.io.*;
import javax.servlet.http.*;
import com.fasterxml.jackson.core.*;
import com.google.appengine.api.datastore.*;
import java.util.logging.Logger;

public class PandemicDealServlet extends HttpServlet
{
	private static final Logger log = Logger.getLogger(PandemicDealServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException
	{
		resp.setContentType("text/json;charset=UTF-8");
		JsonGenerator out = new JsonFactory().
			createJsonGenerator(resp.getWriter()
			);
		out.writeStartObject();
		out.writeStringField("protocolVersion", "1");
		out.writeEndObject();
		out.close();
	}

	static String getRequestContent(HttpServletRequest req)
		throws IOException
	{
		StringBuilder sb = new StringBuilder();
		Reader r = req.getReader();
		char [] cbuf = new char[1024];
		int nread;

		while ( (nread = r.read(cbuf)) != -1 )
		{
			sb.append(cbuf, 0, nread);
		}

		return sb.toString();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException
	{
		String content = getRequestContent(req);
		JsonParser json = new JsonFactory().
			createJsonParser(new StringReader(content));

		String rules = null;
		while (json.nextToken() != null) {
			if (json.getCurrentToken() != JsonToken.FIELD_NAME) { continue; }

			if (json.getCurrentName().equals("rules")) {
				json.nextToken();
				rules = json.getText();
			}
		}

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		try
		{

			//TODO
		}
		finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
}
