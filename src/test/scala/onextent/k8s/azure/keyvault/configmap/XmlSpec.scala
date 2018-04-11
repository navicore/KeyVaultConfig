package onextent.k8s.azure.keyvault.configmap

import org.scalatest._

import scala.collection.mutable

class XmlSpec extends FlatSpec {

  "A Stack" should "pop values in last-in-first-out order" in {
    //noinspection ScalaDeprecation
    val stack = new mutable.Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    //noinspection ScalaDeprecation
    val emptyStack = new mutable.Stack[String]
    assertThrows[NoSuchElementException] {
      emptyStack.pop()
    }
  }
}
