package com.cp.framwork.core

/*
  *
  *@Description 分页对象 .</br>
  *<></>
  *@Author gu
  *@Date 2020/9/5 18:05
  *@Version 1.0.0
  **/
case class QueryResult[T](totalNumber: Int, offset: Int, limit: Int, result: T)
