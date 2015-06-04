package edu.mssm.pharm.maayanlab.Harmonizome.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.mssm.pharm.maayanlab.Harmonizome.util.Constant;
import edu.mssm.pharm.maayanlab.Harmonizome.util.Query;

@WebServlet(urlPatterns = { Constant.PAGES_BASE_URL + "/dataset/*" })
public class DatasetPage extends HttpServlet {

	private static final long serialVersionUID = -652055513491031817L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String queriedSymbol = Query.get(req);
		if (queriedSymbol.equals("")) {
			req.getRequestDispatcher(Constant.TEMPLATE_DIR + "search_landing.jsp").forward(req, resp);
		}
	}
}
