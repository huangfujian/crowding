package com.gx.crowd.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gx.crowd.api.MySqlServerRemote;
import com.gx.crowd.config.AlipayProperties;
import com.gx.crowd.entity.vo.OrderProjectVO;
import com.gx.crowd.entity.vo.OrderVO;
import com.gx.crowd.entity.vo.ReturnJson;
import com.gx.crowd.utils.CrowdConstants;
import com.sun.org.apache.regexp.internal.RE;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 15:45
 */
@Controller
@Slf4j
public class PayController {
    @Autowired
    private AlipayProperties alipayProperties;
    @Autowired
    private MySqlServerRemote mySqlServerRemote;

    @RequestMapping("/generate/order")
    @ResponseBody
    public String generateOrder(OrderVO orderVO, HttpSession session) {
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstants.ORDER_PROJECT_VO);
        String payNumber = UUID.randomUUID().toString().replace("-", "");
        Integer totalAmount = orderProjectVO.getReturnCount() * orderProjectVO.getSupportMoney() + orderProjectVO.getFreight();
        orderVO.setOrderAmount(totalAmount);
        orderVO.setOrderNum(payNumber);
        orderVO.setOrderProjectVO(orderProjectVO);
        session.setAttribute(CrowdConstants.ORDER_VO, orderVO);
        //调用支付宝的接口
        try {
            return alipay(orderVO.getOrderNum(), orderVO.getOrderAmount(), orderProjectVO.getProjectName(), orderProjectVO.getLaunchName());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    private String alipay(String payNumber, Integer totalAmount, String subject, String body) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayProperties.getGatewayUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getMerchantPrivateKey(),
                "json",
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getSignType());
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayProperties.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + payNumber + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        return alipayClient.pageExecute(alipayRequest).getBody();
    }

    @RequestMapping("/notify")
    @ResponseBody
    public void notifyUrl(HttpServletRequest request) {
        try {
//获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    alipayProperties.getAlipayPublicKey(),
                    alipayProperties.getCharset(),
                    alipayProperties.getSignType());
            //调用SDK验证签名
            System.out.println(signVerified);
            if (signVerified) {//验证成功
                //商户订单号
                String out_trade_no = request.getParameter("out_trade_no");
                //支付宝交易号
                String trade_no = request.getParameter("trade_no");
                //交易状态
                String trade_status = request.getParameter("trade_status");
                log.info("订单号:" + out_trade_no);
                log.info("支付宝交易号:" + trade_no);
                log.info("交易状态:" + trade_status);
                log.info("验证成功");
            } else {//验证失败
                log.error("验证失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @RequestMapping("/return")
    @ResponseBody
    public String returnUrl(HttpServletRequest request, HttpSession session) {
        try {
            /* *
             * 功能：支付宝服务器同步通知页面
             * 日期：2017-03-30
             * 说明：
             * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
             * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
             *************************页面功能说明*************************
             * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
             */
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    alipayProperties.getAlipayPublicKey(),
                    alipayProperties.getCharset(),
                    alipayProperties.getSignType()); //调用SDK验证签名
            //——请在这里编写您的程序（以下代码仅作参考）——
            if (signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
                OrderVO orderVO = (OrderVO) session.getAttribute(CrowdConstants.ORDER_VO);
                orderVO.setPayOrderNum(trade_no);
                ReturnJson<String> returnJson = mySqlServerRemote.saveOrderVORemote(orderVO);
                if (returnJson.getStatus()) {
                    //成功
                    return "trade_no: + " + trade_no + " + <br/>out_trade_no: " + out_trade_no + " <br/>total_amount: " + total_amount;
                }
                log.error(returnJson.getMsg());
                return returnJson.getMsg();
            } else {
                log.error("验签失败");
                return "验签失败";
            }
            //——请在这里编写您的程序（以上代码仅作参考）——
        } catch (Exception e) {
//            e.printStackTrace();
            log.error(e.getMessage());
            return e.getMessage();
        }
    }
}
