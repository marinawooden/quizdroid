package edu.uw.ischool.mwoode.quizdroid

interface Downloader {
    fun downloadFile(url: String): Long
}