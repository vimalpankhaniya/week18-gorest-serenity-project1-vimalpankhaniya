package com.gorest.testsuite;


import com.gorest.productandstoreinfo.UserSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCurdTest extends TestBase {

    static String name = "Prime" + TestUtils.getRandomValue();
    static String email = "mann" + TestUtils.getRandomValue() + "@gmail.com";
    static  String gender = "female";
    static  String status = "Active";
    static int userId;


@Steps
    UserSteps userSteps;

    @Title("This will create new Users")
    @Test
    public void test001() {
        ValidatableResponse response = userSteps.createUser(name, email, gender,status );
        response.log().all().statusCode(201);
        userId = response.log().all().extract().path("id");

    }
    @Title("This test will Update the product information")
    @Test
    public void test002() {
        name = name + "_updated";
        userSteps.updateProduct(userId,name,email,gender,status).statusCode(200).log().all();
        HashMap<String,Object> productMapData = userSteps.getProductInfoById(userId);
        Assert.assertThat(productMapData,hasValue(name));
    }
    @Title("Delete the student and verify if the student is deleted!")
    @Test
    public void test003() {
        System.out.println("Id want to delete"+userId);
        userSteps.deleteProduct(userId).statusCode(204);
        userSteps.getProductId(userId).statusCode(404);

    }
}
