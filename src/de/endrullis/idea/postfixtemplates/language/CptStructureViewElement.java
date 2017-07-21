package de.endrullis.idea.postfixtemplates.language;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.*;
import com.intellij.navigation.*;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import de.endrullis.idea.postfixtemplates.language.psi.*;

import java.util.*;

public class CptStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
  private PsiElement element;

  public CptStructureViewElement(PsiElement element) {
    this.element = element;
  }

  @Override
  public Object getValue() {
    return element;
  }

  @Override
  public void navigate(boolean requestFocus) {
    if (element instanceof NavigationItem) {
      ((NavigationItem) element).navigate(requestFocus);
    }
  }

  @Override
  public boolean canNavigate() {
    return element instanceof NavigationItem &&
           ((NavigationItem) element).canNavigate();
  }

  @Override
  public boolean canNavigateToSource() {
    return element instanceof NavigationItem &&
           ((NavigationItem) element).canNavigateToSource();
  }

  @Override
  public String getAlphaSortKey() {
    return element instanceof PsiNamedElement ? ((PsiNamedElement) element).getName() : null;
  }

  @Override
  public ItemPresentation getPresentation() {
    return element instanceof NavigationItem ?
        ((NavigationItem) element).getPresentation() : null;
  }

  @Override
  public TreeElement[] getChildren() {
    if (element instanceof CptFile) {
	    CptTemplate[] templates = PsiTreeUtil.getChildrenOfType(element, CptTemplate.class);
      List<TreeElement> treeElements = new ArrayList<>(templates.length);
      for (CptTemplate template : templates) {
        treeElements.add(new CptStructureViewElement(template));
      }
      return treeElements.toArray(new TreeElement[treeElements.size()]);
    } else {
      return EMPTY_ARRAY;
    }
  }
}