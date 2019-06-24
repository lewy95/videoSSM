<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<%@include file="include/header.jsp"%>

<body class="bg-info dker">
<section id="content" class="m-t-lg wrapper-md animated fadeInUp">
    <div class="container aside-xl">
        <a class="navbar-brand block" href="forehome"><span class="h1 font-bold">Musik</span></a>
        <section class="m-b-lg">
            <header class="wrapper text-center">
                <strong>Sign in to get in touch</strong>
            </header>
            <form action="foreLogin" method="post">
                <div class="form-group">
                    <input type="email" placeholder="Email" class="form-control rounded input-lg text-center no-border"
                           name="email">
                </div>
                <div class="form-group">
                    <input type="password" placeholder="Password" class="form-control rounded input-lg text-center no-border"
                           name="password">
                </div>
                <button type="submit" class="btn btn-lg btn-warning lt b-white b-2x btn-block btn-rounded"><i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg">Sign in</span></button>
                <div class="text-center m-t m-b"><a href="#"><small>Forgot password?</small></a></div>
                <div class="line line-dashed"></div>
                <p class="text-muted text-center"><small>Do not have an account?</small></p>
                <a href="toRegisterPage" class="btn btn-lg btn-info btn-block rounded">Create an account</a>
            </form>
        </section>
    </div>
</section>
<!-- footer -->
<footer id="footer">
    <div class="text-center padder">
        <p>
            <small>Web app framework base on Bootstrap<br>&copy; 2014</small>
        </p>
    </div>
</footer>

<%@include file="include/footer.jsp"%>
