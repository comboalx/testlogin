package test;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SucFilter", urlPatterns = {"/Succeess"})
public class SucFilter implements Filter{

    public SucFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User u=getUserAndPass(login, password);
        if(u !=null){
            HttpSession session =req.getSession(true);
            session.setAttribute("user", u);
            chain.doFilter(request, response);
        }else{
            request.getRequestDispatcher("index.jsp").include(request, response);
        }
    }

    private static User getUserAndPass(String login, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("login", login));
        criteria.add(Restrictions.eq("password", password));
        User u=(User) criteria.uniqueResult();
        return u;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
