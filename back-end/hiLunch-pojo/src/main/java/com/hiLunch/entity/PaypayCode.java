package com.hiLunch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaypayCode {
    //加盟店から提供された一意の支払い取引ID
    private String merchantPaymentId;
    //支払金額
    private Integer amount;
    //固定値: 'ORDER_QR'
    private String codeType = "ORDER_QR";
    //注文内容の説明。表示の見え方を確認する場合は, こちら をご確認ください
    private String orderDescription;
    //デフォルトではfalseとなります。
    // 売上を後で確定する場合はtrueに設定してください（事前認証および取得支払い）
    private Boolean isAuthorization ;
//    //支払い完了後に開くページ/アプリのURL。
//    // これは、Web URLまたはアプリへのディープリンクを設定することができます
//    private String redirectUrl;
//    //支払いがウェブブラウザで発生している場合は「WEB_LINK」、
//    // アプリで支払いが発生している場合は「APP_DEEP_LINK」のいずれかになります。
//    private String redirectType = ;


}
