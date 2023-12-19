package com.nfjh.bank.web;

import com.nfjh.bank.exceptions.MonkeyNotEnoughException;
import com.nfjh.bank.exceptions.TransferException;
import com.nfjh.bank.service.AccountService;
import com.nfjh.bank.service.impl.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/transfer")
public class AccountServlet extends HttpServlet {

    private AccountService as = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response)
            throws ServletException, IOException {
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));


        try {
            as.transfer(fromActno,toActno,money);
            //显示结果
            response.sendRedirect(request.getContextPath() + "/success.html");
        } catch (MonkeyNotEnoughException e) {
            response.sendRedirect(request.getContextPath() + "/MonkeyNotEnoughException.html");
        } catch (TransferException e) {
            response.sendRedirect(request.getContextPath() + "/TransferException.html");
        }catch (Exception e){
            //出现了其他未知的异常
            response.sendRedirect(request.getContextPath() + "/TransferException.html");
        }


    }
}
