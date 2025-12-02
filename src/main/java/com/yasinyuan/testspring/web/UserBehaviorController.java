package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.service.UserBehaviorService;
import com.yasinyuan.testspring.utils.HttpContextUtils;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户行为追踪Controller
 * @author yasinyuan
 * @date 2025-05-02
 */
@RestController
@RequestMapping("/behavior")
public class UserBehaviorController extends AbstractController {

    @Autowired
    private UserBehaviorService userBehaviorService;

    /**
     * 上报用户行为
     */
    @PostMapping("/track")
    public R trackBehavior(@RequestBody Map<String, Object> params) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ip = HttpContextUtils.getIpAddr(request);
        String device = request.getHeader("User-Agent");

        Long userId = getUserId();
        String sessionId = request.getHeader("sessionId");
        String behaviorType = (String) params.get("behaviorType");
        String pageUrl = (String) params.get("pageUrl");
        String pageTitle = (String) params.get("pageTitle");
        String productId = (String) params.get("productId");
        String productName = (String) params.get("productName");
        Map<String, Object> extInfo = (Map<String, Object>) params.get("extInfo");

        userBehaviorService.trackBehavior(userId, sessionId, behaviorType, pageUrl, pageTitle, productId, productName, ip, device, extInfo);
        return R.ok();
    }

    /**
     * 批量上报用户行为
     */
    @PostMapping("/batchTrack")
    public R batchTrackBehavior(@RequestBody Map<String, Object> params) {
        // 批量处理逻辑
        return R.ok();
    }
}
