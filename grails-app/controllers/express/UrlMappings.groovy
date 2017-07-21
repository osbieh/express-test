package express

class UrlMappings {

    static mappings = {
        "/$lang/$controller/$action?/$id?(.$format)?"{
            constraints {
              //  lang(matches:/ar|en/)

            }
        }
        "/"(redirect: "/ar/main")
        "/$lang/500"(view:'error')
        "/$lang/404"(view:'notFound')
        "/$lang/403"(view:'forbidden')
    }
}
