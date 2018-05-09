package com.xcm91.relation.iosdialog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 171842474@qq.com on 2016/3/23.
 */
public class InvestDetialModel  {

    /**
     * product_name : 理财产品名称
     * interest_rate_item : 预期年化利率
     * interest_rate_val : 8.0%
     * added_rate : +2.5%
     * limit_date : 30天
     * proportion : 0.75
     * total_amount : 2,300,000元
     * sale_amount : 68,800元
     * min_investment_amount : 100元
     * max_investment_amount : 1万元
     * calc_interest_date : T(成交日)+1
     * repayment_mode : 一次性还本付息
     * cash_organization : 中国水电建设集团国际工程有限公司承兑
     * sale_date : 2016-03-01
     * status : 1
     * investment_example : 投资一万元，预期受益80.00元
     * default_purchase_amount : 100
     * increasing_amount : 10
     * menu_list : [{"name":"","menu":[{"param_name":"担保方式","param_value":"权利质押担保","icon_url":"","linked_url":"http://www.baidu.com"},{"param_name":"担保财产","param_value":"商业承兑汇票","icon_url":"","linked_url":"http://www.baidu.com"},{"param_name":"承兑人","param_value":"中国水电建设集团国际工程有限公司","icon_url":"","linked_url":"http://www.baidu.com"}]},{"name":"","menu":[{"param_name":"购买记录","param_value":"28人","icon_url":"","linked_url":"xiaocaimi://productRecord?product_id=10038"},{"param_name":"项目介绍","param_value":"","icon_url":"","linked_url":"http://www.baidu.com"},{"param_name":"资产安全","param_value":"","icon_url":"","linked_url":"http://www.baidu.com"}]}]
     */

    private Result result;

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public static class Result {
        private Points point;
        private String product_id;
        private String type;
        private String product_name;
        private String interest_rate_item;
        private String interest_rate_val;
        private String added_rate;
        private String limit_date;
        private String proportion;
        private String total_amount;
        private String sale_amount;
        private String min_investment_amount;
        private String max_investment_amount;
        private String calc_interest_date;
        private String repayment_mode;
        private String cash_organization;
        private String sale_date;
        private String status;
        private String investment_example;
        private String default_purchase_amount;
        private String increasing_amount;
        private String button_text;
        private String warning_text;
        private String warning_url;
        private String appointment_url;
        private String is_old_user;
        private String advert_display_flag;
        private String old_user_amount;
        private BottomSharePoint new_point;
        private RiPoint share_point;
        private List<String> left_promotion;
        private List<String> right_promotion;
        private List<RecommendAmount> recommend_amount_list;
        /*
"operational_item":[{
    "icon_url":"图标地址",
    "content":"运营文案",
    "linked_url":"跳转地址"
    }]
 */

        public String getAdvert_display_flag() {
            return advert_display_flag;
        }

        public void setAdvert_display_flag(String advert_display_flag) {
            this.advert_display_flag = advert_display_flag;
        }

        public String getAppointment_url() {
            return appointment_url;
        }

        public void setAppointment_url(String appointment_url) {
            this.appointment_url = appointment_url;
        }

        public String getWarning_url() {
            return warning_url;
        }

        public void setWarning_url(String warning_url) {
            this.warning_url = warning_url;
        }

        public String getWarning_text() {
            return warning_text;
        }

        public void setWarning_text(String warning_text) {
            this.warning_text = warning_text;
        }

        private List<OperationalItem> operational_item;
        private boolean isShowOperationalItem = false;

        public boolean isShowOperationalItem() {
            return isShowOperationalItem;
        }

        public void setShowOperationalItem(boolean showOperationalItem) {
            isShowOperationalItem = showOperationalItem;
        }

        public List<OperationalItem> getOperational_item() {
            return operational_item;
        }

        public void setOperational_item(List<OperationalItem> operational_item) {
            this.operational_item = operational_item;
        }

        public List<RecommendAmount> getRecommend_amount_list() {
            return recommend_amount_list;
        }

        public void setRecommend_amount_list(List<RecommendAmount> recommend_amount_list) {
            this.recommend_amount_list = recommend_amount_list;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOld_user_amount() {
            return old_user_amount;
        }

        public void setOld_user_amount(String old_user_amount) {
            this.old_user_amount = old_user_amount;
        }

        public String getIs_old_user() {
            return is_old_user;
        }

        public void setIs_old_user(String is_old_user) {
            this.is_old_user = is_old_user;
        }

        public List<String> getLeft_promotion() {
            return left_promotion;
        }

        public void setLeft_promotion(List<String> left_promotion) {
            this.left_promotion = left_promotion;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public List<String> getRight_promotion() {
            return right_promotion;
        }

        public void setRight_promotion(List<String> right_promotion) {
            this.right_promotion = right_promotion;
        }

        public RiPoint getShare_point() {
            return share_point;
        }

        public void setShare_point(RiPoint share_point) {
            this.share_point = share_point;
        }

        public BottomSharePoint getNew_point() {
            return new_point;
        }

        public void setNew_point(BottomSharePoint new_point) {
            this.new_point = new_point;
        }

        /**
         * name :
         * menu : [{"param_name":"担保方式","param_value":"权利质押担保","icon_url":"","linked_url":"http://www.baidu.com"},{"param_name":"担保财产","param_value":"商业承兑汇票","icon_url":"","linked_url":"http://www.baidu.com"},{"param_name":"承兑人","param_value":"中国水电建设集团国际工程有限公司","icon_url":"","linked_url":"http://www.baidu.com"}]
         */


        public static class Points {
            private String point_url;
            private String point_text;
            private String share_title;
            private String share_image_url;
            private String share_content;
            private String share_id;

            private String share_recommend_url;

            public String getShare_id() {
                return share_id;
            }

            public void setShare_id(String share_id) {
                this.share_id = share_id;
            }

            /**
             * "point" :{
             * "point_url" : "跳转地址URL",
             * "point_text" : "文案信息：例如：新用户首次充值....."
             * "share_title":"分享标题",
             * "share_image_url":"分享图片url",
             * "share_content":"分享内容",
             * "share_recommend_url":"分享地址"
             * }
             */
            public String getPoint_url() {
                return point_url;
            }

            public void setPoint_url(String point_url) {
                this.point_url = point_url;
            }

            public String getPoint_text() {
                return point_text;
            }

            public void setPoint_text(String point_text) {
                this.point_text = point_text;
            }

            public String getShare_title() {
                return share_title;
            }

            public void setShare_title(String share_title) {
                this.share_title = share_title;
            }

            public String getShare_image_url() {
                return share_image_url;
            }

            public void setShare_image_url(String share_image_url) {
                this.share_image_url = share_image_url;
            }

            public String getShare_content() {
                return share_content;
            }

            public void setShare_content(String share_content) {
                this.share_content = share_content;
            }

            public String getShare_recommend_url() {
                return share_recommend_url;
            }

            public void setShare_recommend_url(String share_recommend_url) {
                this.share_recommend_url = share_recommend_url;
            }


        }

        public String getButton_text() {
            return button_text;
        }

        public void setButton_text(String button_text) {
            this.button_text = button_text;
        }

        public Points getPoint() {
            return point;
        }

        public void setPoint(Points point) {
            this.point = point;
        }

        private List<MenuList> menu_list;
        private List<ItemList> item_list;

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public void setInterest_rate_item(String interest_rate_item) {
            this.interest_rate_item = interest_rate_item;
        }

        public void setInterest_rate_val(String interest_rate_val) {
            this.interest_rate_val = interest_rate_val;
        }

        public void setAdded_rate(String added_rate) {
            this.added_rate = added_rate;
        }

        public void setLimit_date(String limit_date) {
            this.limit_date = limit_date;
        }

        public void setProportion(String proportion) {
            this.proportion = proportion;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public void setSale_amount(String sale_amount) {
            this.sale_amount = sale_amount;
        }

        public void setMin_investment_amount(String min_investment_amount) {
            this.min_investment_amount = min_investment_amount;
        }

        public void setMax_investment_amount(String max_investment_amount) {
            this.max_investment_amount = max_investment_amount;
        }

        public void setCalc_interest_date(String calc_interest_date) {
            this.calc_interest_date = calc_interest_date;
        }

        public void setRepayment_mode(String repayment_mode) {
            this.repayment_mode = repayment_mode;
        }

        public void setCash_organization(String cash_organization) {
            this.cash_organization = cash_organization;
        }

        public void setSale_date(String sale_date) {
            this.sale_date = sale_date;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setInvestment_example(String investment_example) {
            this.investment_example = investment_example;
        }

        public void setDefault_purchase_amount(String default_purchase_amount) {
            this.default_purchase_amount = default_purchase_amount;
        }

        public void setIncreasing_amount(String increasing_amount) {
            this.increasing_amount = increasing_amount;
        }

        public void setMenu_list(List<MenuList> menu_list) {
            this.menu_list = menu_list;
        }

        public String getProduct_name() {
            return product_name;
        }

        public String getInterest_rate_item() {
            return interest_rate_item;
        }

        public String getInterest_rate_val() {
            return interest_rate_val;
        }

        public String getAdded_rate() {
            return added_rate;
        }

        public String getLimit_date() {
            return limit_date;
        }

        public String getProportion() {
            return proportion;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public String getSale_amount() {
            return sale_amount;
        }

        public String getMin_investment_amount() {
            return min_investment_amount;
        }

        public String getMax_investment_amount() {
            return max_investment_amount;
        }

        public String getCalc_interest_date() {
            return calc_interest_date;
        }

        public String getRepayment_mode() {
            return repayment_mode;
        }

        public String getCash_organization() {
            return cash_organization;
        }

        public String getSale_date() {
            return sale_date;
        }

        public String getStatus() {
            return status;
        }

        public String getInvestment_example() {
            return investment_example;
        }

        public String getDefault_purchase_amount() {
            return default_purchase_amount;
        }

        public String getIncreasing_amount() {
            return increasing_amount;
        }

        public List<MenuList> getMenu_list() {
            return menu_list;
        }

        public List<ItemList> getItemList() {
            return item_list;
        }

        public static class MenuList {
            private String name;
            /**
             * param_name : 担保方式
             * param_value : 权利质押担保
             * icon_url :
             * linked_url : http://www.baidu.com
             */

            private List<Menu> menu;

            public void setName(String name) {
                this.name = name;
            }

            public void setMenu(List<Menu> menu) {
                this.menu = menu;
            }

            public String getName() {
                return name;
            }

            public List<Menu> getMenu() {
                return menu;
            }

            public static class Menu {
                private String param_name;
                private String param_value;
                private String icon_url;
                private String linked_url;

                public void setParam_name(String param_name) {
                    this.param_name = param_name;
                }

                public void setParam_value(String param_value) {
                    this.param_value = param_value;
                }

                public void setIcon_url(String icon_url) {
                    this.icon_url = icon_url;
                }

                public void setLinked_url(String linked_url) {
                    this.linked_url = linked_url;
                }

                public String getParam_name() {
                    return param_name;
                }

                public String getParam_value() {
                    return param_value;
                }

                public String getIcon_url() {
                    return icon_url;
                }

                public String getLinked_url() {
                    return linked_url;
                }
            }
        }

        public static class ItemList {
            private String key;
            private String val;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }
        }

        public static class BottomSharePoint implements Serializable {
            /**
             * "point_url" : "跳转地址URL",
             * "point_text" : "文案信息：例如：新用户首次充值....."
             * "share_title":"分享标题",
             * "share_image_url":"分享图片url",
             * "share_content":"分享内容",
             * "share_recommend_url":"分享地址"
             */
            private String point_url, point_text, share_title, share_image_url, share_content, share_recommend_url,share_id;;

            public String getPoint_url() {
                return point_url;
            }

            public void setPoint_url(String point_url) {
                this.point_url = point_url;
            }

            public String getPoint_text() {
                return point_text;
            }

            public void setPoint_text(String point_text) {
                this.point_text = point_text;
            }

            public String getShare_title() {
                return share_title;
            }

            public void setShare_title(String share_title) {
                this.share_title = share_title;
            }

            public String getShare_image_url() {
                return share_image_url;
            }

            public void setShare_image_url(String share_image_url) {
                this.share_image_url = share_image_url;
            }

            public String getShare_id() {
                return share_id;
            }

            public void setShare_id(String share_id) {
                this.share_id = share_id;
            }

            public String getShare_content() {
                return share_content;
            }

            public void setShare_content(String share_content) {
                this.share_content = share_content;
            }

            public String getShare_recommend_url() {
                return share_recommend_url;
            }

            public void setShare_recommend_url(String share_recommend_url) {
                this.share_recommend_url = share_recommend_url;
            }
        }

        public static class RiPoint {
            private String point_url;
            private String point_text;
            private String share_title;
            private String share_image_url;
            private String share_content;
            private String share_id;
            private String share_recommend_url;

            public String getPoint_url() {
                return point_url;
            }

            public void setPoint_url(String point_url) {
                this.point_url = point_url;
            }

            public String getPoint_text() {
                return point_text;
            }

            public void setPoint_text(String point_text) {
                this.point_text = point_text;
            }

            public String getShare_title() {
                return share_title;
            }

            public void setShare_title(String share_title) {
                this.share_title = share_title;
            }

            public String getShare_image_url() {
                return share_image_url;
            }

            public void setShare_image_url(String share_image_url) {
                this.share_image_url = share_image_url;
            }

            public String getShare_content() {
                return share_content;
            }

            public void setShare_content(String share_content) {
                this.share_content = share_content;
            }

            public String getShare_recommend_url() {
                return share_recommend_url;
            }

            public void setShare_recommend_url(String share_recommend_url) {
                this.share_recommend_url = share_recommend_url;
            }

            public String getShare_id() {
                return share_id;
            }

            public void setShare_id(String share_id) {
                this.share_id = share_id;
            }
        }
    }

    public static class RecommendAmount {
        private String amount;
        private String display;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }
    public static class OperationalItem {
        /*
            "icon_url":"图标地址",
                "content":"运营文案",
                "linked_url":"跳转地址"
         */
        private String icon_url;
        private String content;
        private String linked_url;

        public OperationalItem(String icon_url, String content, String linked_url) {
            this.icon_url = icon_url;
            this.content = content;
            this.linked_url = linked_url;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLinked_url() {
            return linked_url;
        }

        public void setLinked_url(String linked_url) {
            this.linked_url = linked_url;
        }
    }
}
