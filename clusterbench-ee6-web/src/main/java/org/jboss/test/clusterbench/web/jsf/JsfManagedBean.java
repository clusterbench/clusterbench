/*
 * Copyright 2013 Radoslav Husár
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.test.clusterbench.web.jsf;

import java.io.Serializable;
//import java.util.Map;
//import javax.faces.component.UIViewRoot;
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpSession;
import org.jboss.test.clusterbench.common.SerialBean;

public class JsfManagedBean extends SerialBean implements Serializable {
//    public SomeBean() {
//        super();
//        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//        ses.getId();
//    }
//
//    @Override
//    public int getSerialAndIncrement() {
//        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
//        Map<String, Object> map = viewRoot.getViewMap(true);
//        Object o = map.get(this.getClass().getName());
//        SerialBean s;
//
//        if (o == null) {
//            s = new SerialBean();
//        } else {
//            s = (SerialBean) o;
//        }
//
//        int toret = s.getSerial() + 1;
//        s.setSerial(toret);
//
//        // Persist
//        map.put(this.getClass().getName(), s);
//
//        System.out.println("I stored this: " + s.getSerial());
//
//
//        return super.getSerialAndIncrement();
//    }
}
