package com.hiLunch.controller.user;

import com.hiLunch.constant.MessageConstant;
import com.hiLunch.dto.MenuDTO;
import com.hiLunch.dto.OrderDTO;
import com.hiLunch.exception.PaymentErrorException;
import com.hiLunch.properties.PaypayProperties;
import com.hiLunch.result.Result;
import com.hiLunch.service.OrderService;
import com.hiLunch.utils.OrderNumberGenUtil;
import com.hiLunch.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.ne.paypay.ApiClient;
import jp.ne.paypay.ApiException;
import jp.ne.paypay.Configuration;
import jp.ne.paypay.api.PaymentApi;
import jp.ne.paypay.model.MoneyAmount;
import jp.ne.paypay.model.QRCode;
import jp.ne.paypay.model.QRCodeDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/user/order")
@Api(tags="注文した料理に関する")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    PaypayProperties paypayProperties;


    /**
     * 　注文内容をgetする
     * @param
     * @return
     */
    @GetMapping
    public Result<List> getAllOrder(){
        //get info from service
        //TODO 返回前端的时候，相同订单号分组展示问题
        List<OrderVO> list = orderService.getAllOrder();
        return Result.success(list);
    }

    /**
     * 新しい注文がした時
     * @param list
     *
     */
//    @PostMapping("/create")
    //TODO 和购物车的CRUD一起考虑
    // 购物车是redis还是什么形式？从购物车加入，还是传数据到后端
//    public Result createOrder(@RequestBody List<OrderDTO> list){
//        orderService.createOrder(list);
//        return Result.success();
//    }

    /**
     * paypay支払いに行く
     * @param list
     *
     */
    @PostMapping("/pay")
    @ApiOperation("ペイペイの支払い")
    @Transactional
    //どこか失敗するなら、ロールバック
    public Result<String> pay(@RequestBody List<MenuDTO> list) throws ApiException {
        //TODO stocks check
        QRCode qrCode = new QRCode();
        Integer amount = 0;
        for (MenuDTO m:list
             ) {
            amount += m.getPrice();
        }
        log.info("amount:{}",amount);
        qrCode.setAmount(new MoneyAmount().amount(amount).currency(MoneyAmount.CurrencyEnum.JPY));

        //抽出
        String orderNum = OrderNumberGenUtil.generateOrderNumber();
        qrCode.setMerchantPaymentId(orderNum);
        qrCode.setCodeType("ORDER_QR");
        qrCode.setOrderDescription("your all bills");
        qrCode.isAuthorization(false);
        qrCode.setRedirectType(QRCode.RedirectTypeEnum.WEB_LINK);

        ApiClient apiClient = new Configuration().getDefaultApiClient();
        apiClient.setProductionMode(false);
        apiClient.setApiKey(paypayProperties.getApiKey());
        apiClient.setApiSecretKey(paypayProperties.getApiSecretKey());
        apiClient.setAssumeMerchant(paypayProperties.getAssumeMerchant());
        log.info("API Key: {}", paypayProperties.getApiKey());
        log.info("Merchant: {}", apiClient.getAssumeMerchant());


        PaymentApi apiInstance = new PaymentApi(apiClient);
        QRCodeDetails response = apiInstance.createQRCode(qrCode);
        if(!response.getResultInfo().getCode().equals("SUCCESS")){
            throw new PaymentErrorException(response.getResultInfo().getMessage());
        }
        //TODO  if pay successfully, stock-1
        return Result.success(response.getData().getUrl());
    }


    /**
     * cash支払いに行く
     * @param list
     *
     */
    @PostMapping("/cash")
    @ApiOperation("支払い方法は現金で")
    @Transactional
    public Result PayByCash(@RequestBody List<MenuDTO> list){
        //TODO stocks check
        String orderNum = OrderNumberGenUtil.generateOrderNumber();
        orderService.cashOrderNum(list,orderNum);
        //TODO  if pay successfully, stock-1
        return Result.success(orderNum);
    }



}
