package me.ehlxr.token;

/**
 * token版本相关的配置
 * User: erin
 * Date: 14-10-30
 * Time: 下午5:34
 */
public class TokenVersionConfig {

    private int version;//token的版本号
    private String create_token_key;//生成token的key
    private String check_token_key;//自校验token的key
    private String check;//获取自校验位时，取哪些元素

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCreate_token_key() {
        return create_token_key;
    }

    public void setCreate_token_key(String create_token_key) {
        this.create_token_key = create_token_key;
    }

    public String getCheck_token_key() {
        return check_token_key;
    }

    public void setCheck_token_key(String check_token_key) {
        this.check_token_key = check_token_key;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
