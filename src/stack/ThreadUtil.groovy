package stack

class ThreadUtil {
    def static doInject(){
        Thread.metaClass.methodCountInStack={ Set<String> methodNames->
            int count=0
            getStackTrace().each{ StackTraceElement element->
                if(methodNames.contains(element.getMethodName())){
                    count++
                }
            }
            count
        }
    }
}
