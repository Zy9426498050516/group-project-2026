package org.xiaolanjing.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Cart;
import org.xiaolanjing.entity.Customer;
import org.xiaolanjing.entity.Order;
import org.xiaolanjing.entity.OrderItem;
import org.xiaolanjing.mapper.CartMapper;
import org.xiaolanjing.mapper.OrderMapper;
import org.xiaolanjing.mapper.ProductMapper;
import org.xiaolanjing.vo.CartVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车（顾客端）：增删改查 + 下单
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public String cart(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("loginCustomer");
        model.addAttribute("items", cartMapper.findByCustomerId(customer.getId()));
        return "cart";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long productId,
                      @RequestParam(defaultValue = "1") Integer quantity,
                      HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loginCustomer");
        if (quantity == null || quantity < 1) {
            quantity = 1;
        }
        Cart exist = cartMapper.findByCustomerIdAndProductId(customer.getId(), productId);
        if (exist != null) {
            cartMapper.updateQuantity(exist.getId(), exist.getQuantity() + quantity);
        } else {
            Cart cart = new Cart();
            cart.setCustomerId(customer.getId());
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cartMapper.insert(cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Integer quantity) {
        if (quantity == null || quantity < 1) {
            quantity = 1;
        }
        cartMapper.updateQuantity(id, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        cartMapper.deleteById(id);
        return "redirect:/cart";
    }

    /** 下单：把购物车里的商品生成一个订单，并清空购物车、扣减库存 */
    @PostMapping("/checkout")
    public String checkout(@RequestParam(required = false) String receiver,
                           @RequestParam(required = false) String phone,
                           @RequestParam(required = false) String address,
                           HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loginCustomer");
        List<CartVO> items = cartMapper.findByCustomerId(customer.getId());
        if (items == null || items.isEmpty()) {
            return "redirect:/cart";
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CartVO item : items) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        Order order = new Order();
        order.setOrderNo("X" + System.currentTimeMillis());
        order.setCustomerId(customer.getId());
        order.setTotalAmount(total);
        order.setStatus("待发货");
        order.setReceiver(receiver);
        order.setPhone(phone);
        order.setAddress(address);
        orderMapper.insertOrder(order);

        for (CartVO item : items) {
            OrderItem oi = new OrderItem();
            oi.setOrderId(order.getId());
            oi.setProductId(item.getProductId());
            oi.setProductName(item.getProductName());
            oi.setProductImage(item.getImage());
            oi.setPrice(item.getPrice());
            oi.setQuantity(item.getQuantity());
            orderMapper.insertItem(oi);
            // 扣减库存
            int stock = item.getStock() == null ? 0 : item.getStock();
            productMapper.updateStock(item.getProductId(), Math.max(0, stock - item.getQuantity()));
        }

        cartMapper.deleteByCustomerId(customer.getId());
        return "redirect:/cart";
    }
}
