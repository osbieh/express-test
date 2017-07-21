package express

import express.security.User
import sun.misc.BASE64Encoder


class CommonTagLib {
    def springSecurityService
    static namespace = "com"
    // static defaultEncodeAs = [taglib:'html']

    def currentName = {attrs ->
        if(springSecurityService.isLoggedIn())
            out <<springSecurityService.principal?.username
    }

    def currentUserPic = {attrs ->
        def obj = User.findByUsername(springSecurityService.principal?.username)
        def img= obj?.userPic
        out << '<img width="42" height="42" class="img-circle" src="data:image/png;base64,'
        out<< img?.binaryStream?.bytes?.encodeBase64()
        out<< '">'




    }

    def productPic = {attrs ->
        def obj = User.findByUsername(springSecurityService.principal?.username)
        def img= obj?.userPic
        out << '<img width="150" height="100" class="img-responsive pic-bordered" src="data:image/png;base64,'
        out<< img?.binaryStream?.bytes?.encodeBase64()
        out<< '" alt>'




    }


    def renderImageLoader={attrs ->
        out << '<div id=\'ajax_loader\' style="z-index:10100;position: absolute; left: 44%; top: 50%; display: none;">\n' +
                '<img src="/express/assets/icons/LoadingStarGreen.gif"/>\n' +
                '</div>';
    }

   def  typeahead = {attrs,body ->

       if(params?.lang=='ar')
           attrs.direction="rtl"
       else
           attrs.direction="ltr"

       attrs.url= createLink(uri:"/$params.lang/contact/contactTypeahead")

       out<< render(template:"/templates/taglib/typeHead",model:attrs )
   }


    def  accountTypeAhead = {attrs,body ->

        if(params?.lang=='ar')
            attrs.direction="rtl"
        else
            attrs.direction="ltr"

        attrs.url= createLink(uri:"/$params.lang/account/accountTypeahead")

        out<< render(template:"/templates/taglib/accountTypeHead",model:attrs )
    }


    def  productTypeAhead = {attrs,body ->

        if(params?.lang=='ar')
            attrs.direction="rtl"
        else
            attrs.direction="ltr"

        attrs.url= createLink(uri:"/$params.lang/product/productTypeahead")

        out<< render(template:"/templates/taglib/productTypeHead",model:attrs )
    }

    def  phoneTable = {attrs,body ->

        if(params?.lang=='ar')
            attrs.direction="rtl"
        else
            attrs.direction="ltr"

        //attrs.url= createLink(uri:"/$params.lang/contact/contactTypeahead")

        out<< render(template:"/templates/taglib/phoneTable",model:attrs )
    }


    def  banksTypeAhead = {attrs,body ->

        if(params?.lang=='ar')
            attrs.direction="rtl"
        else
            attrs.direction="ltr"

        attrs.url= createLink(uri:"/$params.lang/category/banksTypeAHead")

        out<< render(template:"/templates/taglib/banksTypeHead",model:attrs )
    }


}
