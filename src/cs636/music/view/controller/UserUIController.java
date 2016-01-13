package cs636.music.view.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.Cart;
import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;
import cs636.music.service.ServiceException;
import cs636.music.service.UserService;
import cs636.music.service.UserServiceAPI;

@Controller

public class UserUIController {

	private final String HOME_PAGE_URL = "/user/home";
	private final String CATALOG_PAGE_URL = "/user/catalog";
	private final String CART_PAGE_URL = "/user/cart";
	private final String PRODUCT_PAGE_URL = "/user/product";
	private final String REGISTER_URL = "/user/register";
	private final String INVOICE_URL="/user/invoice";
	
	private UserServiceAPI userService = MusicSystemConfig.getUserService();

	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public String ViewSignup(Model model) {

		model.addAttribute("user", new User());
		return "/user/register";
	}
	
	@RequestMapping(value = "/register.htm", method = RequestMethod.POST)
	public String Signup(@ModelAttribute("user") @Valid User user,
			BindingResult br, Model model, HttpSession session) {
		
		if (br.hasErrors()) {
			return REGISTER_URL;
		} else {
			session.setAttribute("cart", new Cart());
			
			try {
				User pressetedUser=MusicSystemConfig.getUserService().registerUser(user.getFirstname(), user.getLastname(), user.getEmailAddress());
				session.setAttribute("user", pressetedUser);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
			return HOME_PAGE_URL;
		}
	}
	@RequestMapping(value = { "/user/home.htm" })
	public String viewHome(HttpSession session) {
		Cart cart=(Cart)session.getAttribute("cart");
		if (cart!=null)
		{
			return HOME_PAGE_URL;
		}
		else
		{
			session.setAttribute("cart", new Cart());
		}
		
		return HOME_PAGE_URL;
	}

	@RequestMapping(value = "/user/checkOut.htm", method = RequestMethod.GET)
	public String checkOut(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Cart cart = (Cart) request.getSession().getAttribute("cart");
		User user = (User) request.getSession().getAttribute("user");
		if(!cart.getItems().isEmpty())
		{
			try {
				  MusicSystemConfig.getUserService().checkout(cart, user);
				  request.getSession().removeAttribute("cart");
				  return INVOICE_URL;
				} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return CATALOG_PAGE_URL;
	}

	

	
	@RequestMapping(value = { "/user/catalog.htm" })
	public String viewCatalog(Map<String, Object> map) {
		try {
			Set<Product> products = userService.getProductList();
			map.put("products", products);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return CATALOG_PAGE_URL;
	}

	@RequestMapping(value = { "/user/cart.htm" })
	public String viewCart(HttpServletRequest request ,HttpSession session,Map<String, Object> map) {

		String proCode=request.getParameter("prodCode");
		System.out.println("the product code is =" +proCode);

		if (proCode != null){
			try {
				Product product = MusicSystemConfig.getUserService().getProduct(proCode);
				Cart cart = (Cart) session.getAttribute("cart");

				MusicSystemConfig.getUserService().addItemtoCart(product, cart,1);
				

			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return CART_PAGE_URL;
	}

	
	@RequestMapping(value = { "/user/product.htm" })
	
	public String viewDetails(@RequestParam("prodCode") String prodCode, Map<String, Object> map) {
		try {
			Product product = userService.getProduct(prodCode);
			map.put("product", product);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PRODUCT_PAGE_URL;
	}

	@RequestMapping(value = "/user/download.htm", method = RequestMethod.GET)
	public String login(@RequestParam("prodCode") String prodCode,@RequestParam("track") String trackName,
			Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) {

		
		Track track=null;
		
		try {
			
			
			User user = (User) request.getSession().getAttribute("user");
			
			if (user != null) {
				
				Product product = userService.getProduct(prodCode);
				Iterator<Track> i= product.getTracks().iterator();
				while(i.hasNext())
				{
					Track t=i.next();
					if(t.getSampleFilename().equals(trackName))
					{
						track=t;
					}
				}
				userService.addDownload(user, track);
				response.sendRedirect(request.getContextPath()+"/user/product.htm?prodCode="+prodCode);

			} else {
				System.out.println("null user");
			}

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/user/product";

	}

}
