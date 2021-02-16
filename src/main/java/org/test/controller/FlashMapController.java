package org.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName flashMapController
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/2/16 15:27
 * @Version 1.0
 **/
@Controller
public class FlashMapController {

    /**
     * 在重定向时，如果需要传递参数，但是又不想放在地址栏中，我们就可以通过 flashMap 来传递参数
     */
    @PostMapping("/order")
    public String order(HttpServletRequest req) {
        FlashMap flashMap = (FlashMap) req.getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE);
        flashMap.put("name", "江南一点雨");
        return "redirect:/orderList";
    }

    /**
     * 首先在 order 接口中，获取到 flashMap 属性，然后存入需要传递的参数，
     * 这些参数最终会被 SpringMVC 自动放入重定向接口的 Model 中，
     * 这样我们在 orderlist 接口中，就可以获取到该属性了。
     *
     * @param model
     * @return
     */
    @GetMapping("/orderList")
    @ResponseBody
    public String orderList(Model model) {
        return (String) model.getAttribute("name");
    }

    /**
     * 简化写法
     * RedirectAttributes 中有两种添加参数的方式：
     * 1,addFlashAttribute：将参数放到 flashMap 中。
     * 2,addAttribute：将参数放到 URL 地址中。
     */

    @PostMapping("/order2")
    public String order2(RedirectAttributes attr) {
        attr.addFlashAttribute("site", "www.javaboy.org");
        attr.addAttribute("name", "微信公众号：江南一点雨");
        return "redirect:/orderList2";
    }

    @GetMapping("/orderList2")
    @ResponseBody
    public String orderList2(Model model) {
        return (String) model.getAttribute("site");
    }

    /**
     *1,首先调用 retrieveFlashMaps 方法从 session 中获取到所有的 FlashMap。
     * 2,调用 getExpiredFlashMaps 方法获取所有过期的 FlashMap，FlashMap 默认的过期时间是 180s。
     * 3,获取和当前请求匹配的getMatchingFlashMap，具体的匹配逻辑就两点：重定向地址要和当前请求地址相同；
     *   预设参数要相同。一般来说我们不需要配置预设参数，所以这一条可以忽略。如果想要设置，则首先给 flashMap 设置，
     *   像这样：flashMap.addTargetRequestParam("aa", "bb");，然后在重定向的地址栏也加上这个参数：
     *   return "redirect:/orderlist?aa=bb"; 即可。
     * 4,将获取到的匹配的 FlashMap 对象放入 mapsToRemove 集合中（这个匹配到的 FlashMap 即将失效，放入集合中一会被清空）。
     * 5,将 allFlashMaps 集合中的所有 mapsToRemove 数据清空，同时调用 updateFlashMaps 方法更新 session 中的 FlashMap。
     * 6,最终将匹配到的 flashMap 返回。
     */
}
