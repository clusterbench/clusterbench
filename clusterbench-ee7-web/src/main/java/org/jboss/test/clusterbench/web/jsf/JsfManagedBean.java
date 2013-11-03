/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
