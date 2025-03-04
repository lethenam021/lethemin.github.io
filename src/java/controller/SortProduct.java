////package controller;
////
////import dal.DAO;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.annotation.WebServlet;
////import jakarta.servlet.http.HttpServlet;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import java.io.IOException;
////import java.util.List;
////import mol.Product;
////
////@WebServlet(name = "SortProduct", urlPatterns = {"/sortproduct"})
////public class SortProduct extends HttpServlet {
////
////    @Override
////    protected void doGet(HttpServletRequest request, HttpServletResponse response)
////            throws ServletException, IOException {
////        // Lấy thông tin sort và trang hiện tại từ request
////        String sortType = request.getParameter("sort");
////        String indexPage = request.getParameter("index");
////
////        if (indexPage == null) {
////            indexPage = "1";
////        }
////        int index = Integer.parseInt(indexPage);
////
////        DAO dao = new DAO();
////
////        // Tổng số sản phẩm dựa theo tiêu chí sắp xếp
////        int count = dao.getAllProducts().size();
////        int endPage = (int) Math.ceil(count / 8.0);
////
////        // Lấy danh sách sản phẩm theo trang và tiêu chí sắp xếp
////        List<Product> sortedProducts = null;
////        switch (sortType) {
////            case "price_asc":
////                sortedProducts = dao.SortProductbyPriceASC(index);
////                break;
//////            case "price_desc":
//////                sortedProducts = dao.PagingAndSortProducts(index, "price DESC");
//////                break;
//////            case "BestSeller_desc":
//////                sortedProducts = dao.PagingAndSortProducts(index, "best_seller DESC");
//////                break;
//////            case "Feedback_desc":
//////                sortedProducts = dao.PagingAndSortProducts(index, "average_feedback DESC");
//////                break;
//////            default:
//////                sortedProducts = dao.PagingAndSortProducts(index, "id ASC"); // Mặc định sắp xếp theo ID
//////                break;
////        }
////
////        // Gán các thuộc tính vào request để hiển thị trên giao diện
////        request.setAttribute("endP", endPage);
////        request.setAttribute("currentPage", index);
////        request.setAttribute("lists", sortedProducts);
////        request.setAttribute("sortType", sortType);
////
////        // Forward đến trang JSP
////        request.getRequestDispatcher("AllProducts.jsp").forward(request, response);
////    }
////
////    @Override
////    protected void doPost(HttpServletRequest request, HttpServletResponse response)
////            throws ServletException, IOException {
////        doGet(request, response);
////    }
////}
//<!-- sort List -->
//        <div class="container">
//            <div class="row">
//                <c:forEach items="${sorts}" var="p">
//                    <div class="col-12 col-md-6 col-lg-3 mb-4">
//                        <div class="card shadow-sm border-0">
//                            <img class="card-img-top product-img" src="<c:out value='${p.image}'/>" alt="Product Image">
//                            <div class="card-body text-center">
//                                <h6 class="card-title text-truncate">
//                                    <a href="detail?id=<c:out value='${p.id}'/>" class="text-dark"><c:out value='${p.name}'/></a>
//                                </h6>
//                                <p class="btn btn-outline-danger btn-block"><c:out value='${p.price}'/> $</p>
//                                <form action="cart" method="post">
//                                    <input type="hidden" name="id" value="${p.id}">
//                                    <input type="hidden" name="quantity" value="1">
//                                    <button type="submit" class="btn btn-dark btn-block">
//                                        <i class="fa fa-shopping-cart"></i> Add to Cart
//                                    </button>
//                                </form>
//                            </div>
//                        </div>
//                    </div>
//                </c:forEach>
//            </div>
//        </div>
//
