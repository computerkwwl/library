/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.view.tag;

import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.page.Page;
import org.beangle.struts2.view.component.Agent;
import org.beangle.struts2.view.component.Anchor;
import org.beangle.struts2.view.component.Checkbox;
import org.beangle.struts2.view.component.Checkboxes;
import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.component.Css;
import org.beangle.struts2.view.component.Date;
import org.beangle.struts2.view.component.Dialog;
import org.beangle.struts2.view.component.Div;
import org.beangle.struts2.view.component.Email;
import org.beangle.struts2.view.component.Field;
import org.beangle.struts2.view.component.Foot;
import org.beangle.struts2.view.component.Form;
import org.beangle.struts2.view.component.Formfoot;
import org.beangle.struts2.view.component.Grid;
import org.beangle.struts2.view.component.Head;
import org.beangle.struts2.view.component.Iframe;
import org.beangle.struts2.view.component.Messages;
import org.beangle.struts2.view.component.Module;
import org.beangle.struts2.view.component.Navitem;
import org.beangle.struts2.view.component.Navmenu;
import org.beangle.struts2.view.component.Pagebar;
import org.beangle.struts2.view.component.Password;
import org.beangle.struts2.view.component.Radio;
import org.beangle.struts2.view.component.Radios;
import org.beangle.struts2.view.component.Reset;
import org.beangle.struts2.view.component.Select;
import org.beangle.struts2.view.component.Select2;
import org.beangle.struts2.view.component.Startend;
import org.beangle.struts2.view.component.Submit;
import org.beangle.struts2.view.component.Tab;
import org.beangle.struts2.view.component.Tabs;
import org.beangle.struts2.view.component.Textarea;
import org.beangle.struts2.view.component.Textfield;
import org.beangle.struts2.view.component.Textfields;
import org.beangle.struts2.view.component.Toolbar;
import org.beangle.struts2.view.component.Validity;

import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.utility.StringUtil;

/**
 * beangle tag models
 * 
 * @author chaostone
 * @since 3.0.2
 */
public class BeangleModels extends AbstractModels {

  public BeangleModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    super(stack, req, res);
  }

  public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    return new BeangleModels(stack, req, res);
  }

  public String url(String url) {
    return render.render(req.getRequestURI(), url);
  }

  public java.util.Date getNow() {
    return new java.util.Date();
  }

  /**
   * query string and form control
   */
  public String getParamstring() {
    StringWriter sw = new StringWriter();
    Enumeration<?> em = req.getParameterNames();
    while (em.hasMoreElements()) {
      String attr = (String) em.nextElement();
      String value = req.getParameter(attr);
      if (attr.equals("x-requested-with")) continue;
      sw.write(attr);
      sw.write('=');
      sw.write(StringUtil.javaScriptStringEnc(value));
      if (em.hasMoreElements()) sw.write('&');
    }
    return sw.toString();
  }

  public boolean isPage(Object data) {
    return data instanceof Page<?>;
  }

  public String text(String name) {
    return textResource.getText(name, name);
  }

  public String text(String name, Object arg0) {
    return textResource.getText(name, name, arg0);
  }

  public String text(String name, Object arg0, Object arg1) {
    return textResource.getText(name, name, arg0, arg1);
  }

  /**
   * Return useragent component.
   */
  public TagModel getAgent() {
    return get(Agent.class);
  }

  public TagModel getHead() {
    return get(Head.class);
  }

  public TagModel getDialog() {
    return get(Dialog.class);
  }

  public TagModel getCss() {
    return get(Css.class);
  }

  public TagModel getIframe() {
    return get(Iframe.class);
  }

  public TagModel getFoot() {
    return get(Foot.class);
  }

  public TagModel getForm() {
    return get(Form.class);
  }

  public TagModel getFormfoot() {
    return get(Formfoot.class);
  }

  public TagModel getSubmit() {
    return get(Submit.class);
  }

  public TagModel getReset() {
    return get(Reset.class);
  }

  public TagModel getToolbar() {
    return get(Toolbar.class);
  }

  public TagModel getTabs() {
    return get(Tabs.class);
  }

  public TagModel getTab() {
    return get(Tab.class);
  }

  public TagModel getGrid() {
    return get(Grid.class);
  }

  public TagModel getGridbar() {
    return get(Grid.Bar.class);
  }

  public TagModel getGridfilter() {
    return get(Grid.Filter.class);
  }

  public TagModel getRow() {
    return get(Grid.Row.class);
  }

  public TagModel getCol() {
    TagModel model = models.get(Grid.Col.class);
    if (null == model) {
      // just for performance
      model = new TagModel(stack) {
        @Override
        protected Component getBean() {
          return new Grid.Col(stack);
        }
      };
      models.put(Grid.Col.class, model);
    }
    return model;
  }

  public TagModel getTreecol() {
    return get(Grid.Treecol.class);
  }

  public TagModel getBoxcol() {
    return get(Grid.Boxcol.class);
  }

  public TagModel getPagebar() {
    return get(Pagebar.class);
  }

  public TagModel getPassword() {
    return get(Password.class);
  }

  public TagModel getA() {
    TagModel model = models.get(Anchor.class);
    if (null == model) {
      model = new TagModel(stack) {
        protected Component getBean() {
          return new Anchor(stack);
        }
      };
      models.put(Anchor.class, model);
    }
    return model;
  }

  public TagModel getMessages() {
    return get(Messages.class);
  }

  public TagModel getTextfield() {
    return get(Textfield.class);
  }

  public TagModel getEmail() {
    return get(Email.class);
  }

  public TagModel getTextarea() {
    return get(Textarea.class);
  }

  public TagModel getField() {
    return get(Field.class);
  }

  public TagModel getTextfields() {
    return get(Textfields.class);
  }

  public TagModel getDatepicker() {
    return get(Date.class);
  }

  public TagModel getDiv() {
    return get(Div.class);
  }

  public TagModel getSelect() {
    return get(Select.class);
  }

  public TagModel getSelect2() {
    return get(Select2.class);
  }

  public TagModel getModule() {
    return get(Module.class);
  }

  public TagModel getNavmenu() {
    return get(Navmenu.class);
  }

  public TagModel getNavitem() {
    return get(Navitem.class);
  }

  public TagModel getRadio() {
    return get(Radio.class);
  }

  public TagModel getRadios() {
    return get(Radios.class);
  }

  public TagModel getStartend() {
    return get(Startend.class);
  }

  public TagModel getCheckbox() {
    return get(Checkbox.class);
  }

  public TagModel getCheckboxes() {
    return get(Checkboxes.class);
  }

  public TagModel getValidity() {
    return get(Validity.class);
  }
}
