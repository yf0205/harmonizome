package edu.mssm.pharm.maayanlab.Harmonizome.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.mssm.pharm.maayanlab.Harmonizome.model.HgncRootFamily;
import edu.mssm.pharm.maayanlab.Harmonizome.util.Constant;

@WebServlet(urlPatterns = {
	"/" + Constant.API_URL + "/" + HgncRootFamily.ENDPOINT,
	"/" + Constant.API_URL + "/" + HgncRootFamily.ENDPOINT + "/"
})
public class HgncRootFamilyListApi extends HttpServlet {

	private static final long serialVersionUID = -5751207637748524463L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ListApi.doGet(request, response, HgncRootFamily.class, HgncRootFamily.ENDPOINT);
	}
}
